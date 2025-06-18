package com.ecomarket.notificacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Notificaciones - EcoMarket SPA")
                .version("1.0")
                .description("Microservicio para el envío y registro de notificaciones por correo electrónico."));
    }
}