package com.demo.filters;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    private static final Pattern API_DOCS_PATTERN = Pattern.compile("/zx-[^/]+/v3/api-docs.*");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = UUID.randomUUID().toString();

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 检查请求路径是否匹配排除的模式
        if (API_DOCS_PATTERN.matcher(path).matches()) {
            return chain.filter(exchange); // 如果匹配，跳过日志记录，继续过滤链
        }

        // 给请求添加唯一请求ID
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("Request-ID", requestId)
                .build();
                
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        // 记录请求信息
        logRequest(mutatedRequest, requestId);

        // 包装响应对象
        ServerHttpResponse originalResponse = mutatedExchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            @NonNull
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);

                        logger.info("[{}]响应信息：Response Status Code: {}, Headers: {}, Body: {}",
                                requestId, getStatusCode(), getHeaders(), responseBody);

                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body);
            }
        };

        // 执行过滤器链并记录响应信息
        return chain.filter(mutatedExchange.mutate().response(decoratedResponse).build());
    }

    private void logRequest(ServerHttpRequest request, String requestId) {
        logger.info("[{}]请求信息：Method: {}, URI: {}, Headers: {}, Query Params: {}, Request Body: {}",
                requestId, request.getMethod(), request.getURI(), request.getHeaders(), request.getQueryParams(), request.getBody());
    }

    @Override
    public int getOrder() {
        return -1; // 确保此过滤器首先执行
    }
}