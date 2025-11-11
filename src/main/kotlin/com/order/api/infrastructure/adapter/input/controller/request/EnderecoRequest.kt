package com.order.api.infrastructure.adapter.input.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Endereço de entrega")
data class EnderecoRequest(
    @field:Schema(description = "Rua", example = "Rua A")
    @field:NotBlank
    val rua: String,

    @field:Schema(description = "Número", example = "100")
    @field:NotBlank
    val numero: String,

    @field:Schema(description = "Complemento", example = "Apto 10")
    val complemento: String?,

    @field:Schema(description = "Bairro", example = "Centro")
    @field:NotBlank
    val bairro: String,

    @field:Schema(description = "Cidade", example = "São Paulo")
    @field:NotBlank
    val cidade: String,

    @field:Schema(description = "Estado", example = "SP")
    @field:NotBlank
    val estado: String,

    @field:Schema(description = "CEP", example = "01000-000")
    @field:NotBlank
    val cep: String
)