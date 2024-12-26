package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ZX-APP API")
                        .description("ZX-APP service documentation")
                        .version("v1.0"))
                .addServersItem(new Server()
                        .url("/zx-app")
                        .description("Gateway Route"));
    }

    // @Bean
    // public GroupedOpenApi hideApis() {
    //     return GroupedOpenApi.builder().group("zx-app")
    //             .packagesToScan("com.demo.controller")
    //             .pathsToExclude("/test/v2/**", "/**/test/**")
    //             .pathsToMatch("/**")
    //             .build();
    // }

}