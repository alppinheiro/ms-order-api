package com.order.api.infrastructure.adapter.input.controller.request

import jakarta.validation.constraints.NotBlank

data class EnderecoRequest(
    @field:NotBlank
    val rua: String,

    @field:NotBlank
    val numero: String,

    val complemento: String?,

    @field:NotBlank
    val bairro: String,

    @field:NotBlank
    val cidade: String,

    @field:NotBlank
    val estado: String,

    @field:NotBlank
    val cep: String
)