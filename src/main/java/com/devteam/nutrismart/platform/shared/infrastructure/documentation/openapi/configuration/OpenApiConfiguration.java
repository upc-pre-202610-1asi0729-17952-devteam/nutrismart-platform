package com.devteam.nutrismart.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI 3.0 para la plataforma NutriSmart.
 * <p>
 * Define los metadatos globales de la API (título, descripción, versión, contacto,
 * licencia), los servidores disponibles (local, staging, producción) y el esquema
 * de seguridad JWT Bearer utilizado en todos los endpoints.
 * </p>
 */
@Configuration
public class OpenApiConfiguration {

    @Value("${spring.application.name}")
    String applicationName;

    @Value("${documentation.application.description}")
    String applicationDescription;

    @Value("${documentation.application.version}")
    String applicationVersion;

    @Bean
    public OpenAPI nutriSmartPlatformOpenApi() {
        var openApi = new OpenAPI();

        openApi.info(new Info()
                .title(this.applicationName)
                .description(this.applicationDescription)
                .version(this.applicationVersion));

        openApi.servers(List.of(
                new Server()
                        .url("http://localhost:8080")
                        .description("Local Development Environment"),
                new Server()
                        .url("https://smart-api.nutriproject.xyz")
                        .description("Production Environment")
        ));

        final String securitySchemeName = "bearerAuth";

        openApi.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Bearer token for API authentication")));

        return openApi;
    }
}
