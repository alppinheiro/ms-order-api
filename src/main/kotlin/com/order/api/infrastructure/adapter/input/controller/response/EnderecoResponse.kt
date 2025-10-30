package com.order.api.infrastructure.adapter.input.controller.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Endereço de entrega - response")
data class EnderecoResponse(
    @field:Schema(description = "Rua", example = "Rua A")
    val rua: String,

    @field:Schema(description = "Número", example = "100")
    val numero: String,

    @field:Schema(description = "Complemento", example = "Apto 10")
    val complemento: String?,

    @field:Schema(description = "Bairro", example = "Centro")
    val bairro: String,

    @field:Schema(description = "Cidade", example = "São Paulo")
    val cidade: String,

    @field:Schema(description = "Estado", example = "SP")
    val estado: String,

    @field:Schema(description = "CEP", example = "01000-000")
    val cep: String
)