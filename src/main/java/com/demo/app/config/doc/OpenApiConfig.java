package com.demo.app.config.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    @Value("${app.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI baseOpenApi() {
        var localServer = new Server();
        localServer.setUrl(devUrl);
        localServer.setDescription("Server URL in Development Environment");

        var info = new Info()
                .title("API Docs")
                .version("1.0.0")
                .description("This API exposes endpoints for users to manage their tasks.");

        return new OpenAPI().info(info).servers(List.of(localServer));

    }

}
