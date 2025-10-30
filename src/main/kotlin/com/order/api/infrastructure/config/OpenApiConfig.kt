package com.order.api.infrastructure.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        val info = Info()
            .title("ms-order-api")
            .version("0.0.1")
            .description("API de gerenciamento de pedidos (Orders) - microserviço exemplo com Kotlin + Spring Boot")
            .contact(Contact().name("Equipe de Dev").email("dev@example.com"))
            .license(License().name("MIT").url("https://opensource.org/licenses/MIT"))

        val serverLocal = Server().url("http://localhost:8080").description("Local server")

        val tagOrders = Tag().name("Orders").description("Operações relacionadas a pedidos (create/get)")

        return OpenAPI()
            .info(info)
            .addServersItem(serverLocal)
            .addTagsItem(tagOrders)
    }
}
