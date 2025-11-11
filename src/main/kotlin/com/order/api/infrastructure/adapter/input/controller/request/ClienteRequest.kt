package com.order.api.infrastructure.adapter.input.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Dados do cliente")
data class ClienteRequest(
    @field:Schema(description = "Nome do cliente", example = "João Silva")
    @field:NotBlank
    val nome: String,

    @field:Schema(description = "Email do cliente", example = "joao@example.com")
    @field:Email
    @field:NotBlank
    val email: String,

    @field:Schema(description = "CPF do cliente", example = "123.456.789-00")
    @field:NotBlank
    val cpf: String
)