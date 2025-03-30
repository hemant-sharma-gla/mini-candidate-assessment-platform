package com.assessment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.assessment.constants.ApplicationConstants.ASSIGNMENT_SERVICE;
import static com.assessment.constants.ApplicationConstants.ASSIGNMENT_SERVICE_DESCRIPTION;
import static com.assessment.constants.ApplicationConstants.ASSIGNMENT_URL;
import static com.assessment.constants.ApplicationConstants.BEARER;
import static com.assessment.constants.ApplicationConstants.BEARER_AUTH;
import static com.assessment.constants.ApplicationConstants.JWT;
import static com.assessment.constants.ApplicationConstants.SERVICE_BASE_URL;
import static com.assessment.constants.ApplicationConstants.SERVICE_VERSION;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 * This class configures the Swagger UI with custom settings including server URL,
 * security scheme, and API information.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates and configures a custom OpenAPI specification for the application.
     * This configuration includes:
     * - Server URL configuration
     * - JWT Bearer token authentication scheme
     * - API information including version, title, description, and contact details
     *
     * @return OpenAPI object containing the complete Swagger configuration
     */
    @Bean
    public OpenAPI customOpenAPIConfig() {
        io.swagger.v3.oas.models.servers.Server server = new Server();
        server.setUrl(SERVICE_BASE_URL);
        final String securitySchemeName = BEARER_AUTH;
        return new OpenAPI()
                .addServersItem(server)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new io.swagger.v3.oas.models.security.SecurityScheme().name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP).scheme(BEARER).bearerFormat(JWT)
                                .in(SecurityScheme.In.HEADER)))
                .info(new Info().version(SERVICE_VERSION).title(ASSIGNMENT_SERVICE)
                        .description(ASSIGNMENT_SERVICE_DESCRIPTION)
                        .contact(new Contact().name(ASSIGNMENT_SERVICE).url(ASSIGNMENT_URL))
                );
    }
} 