package com.greedy.homepage.common.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private final SwaggerDescription swaggerDescription;

    @Value("${swagger.base-url}")
    private String swaggerServerUrl;

    public SwaggerConfig(SwaggerDescription swaggerDescription) {
        this.swaggerDescription = swaggerDescription;
    }

    // 서버 URL 설정
    @Bean
    public OpenApiCustomizer serverOpenApiCustomizer() {
        return openApi -> openApi.setServers(
                List.of(new Server().url(swaggerServerUrl))
        );
    }

    // 실제 Swagger UI의 Info 설정
    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("greedy-homepage API 명세서")
                        .description(swaggerDescription.getDescription())
                );
    }
}
