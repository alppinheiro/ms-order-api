package com.order.api.infrastructure.adapter.input.controller.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Dados do cliente")
data class ClienteRequest(
    @field:Schema(description = "Nome do cliente", example = "João Silva")
    val nome: String,

    @field:Schema(description = "Email do cliente", example = "joao@example.com")
    val email: String,

    @field:Schema(description = "CPF do cliente", example = "123.456.789-00")
    val cpf: String
)