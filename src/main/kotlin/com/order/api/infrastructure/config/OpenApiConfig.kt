package com.order.api.infrastructure.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("ms-order-api")
                    .version("0.0.1")
                    .description("API de gerenciamento de pedidos")
                    .contact(Contact().name("Equipe de Dev").email("dev@example.com"))
            )
    }
}

