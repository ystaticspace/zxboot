package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class SpringDocConfig implements WebFluxConfigurer {
    // 移除之前的配置，让 Gateway 只作为代理
}