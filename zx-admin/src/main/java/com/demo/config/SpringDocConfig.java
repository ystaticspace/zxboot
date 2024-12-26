package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ZX-ADMIN API")
                        .description("ZX-ADMIN service documentation")
                        .version("v1.0"))
                .addServersItem(new Server()
                        .url("/zx-admin")
                        .description("Gateway Route"));
    }

    // @Bean
    // public GroupedOpenApi hideApis() {
    //     return GroupedOpenApi.builder().group("zx-admin")
    //             .packagesToScan("com.demo.controller")
    //             .pathsToExclude("/test/v2/**", "/**/test/**")
    //             .pathsToMatch("/**")
    //             .build();
    // }

}