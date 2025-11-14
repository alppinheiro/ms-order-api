package com.order.api.infrastructure.adapter.input.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ClienteRequest(
    @field:NotBlank
    val nome: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val cpf: String
)