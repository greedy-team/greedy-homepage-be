package com.greedy.homepage.common.config.swagger;

import com.greedy.homepage.common.exception.APIErrorResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
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

        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(APIErrorResponse.class));

        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("greedy-homepage API 명세서")
                        .description(swaggerDescription.getDescription())
                )
                .components(new Components()
                        .addSchemas("APIErrorResponse", resolvedSchema.schema)
                );
    }
}
