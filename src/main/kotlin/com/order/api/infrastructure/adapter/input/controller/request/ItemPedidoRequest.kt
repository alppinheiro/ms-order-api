package com.order.api.infrastructure.adapter.input.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Schema(description = "Item do pedido")
data class ItemPedidoRequest(
    @field:Schema(description = "Nome do produto", example = "Caneca")
    @field:NotBlank
    val produto: String,

    @field:Schema(description = "Quantidade", example = "2")
    @field:Positive
    val quantidade: Int,

    @field:Schema(description = "Preço unitário", example = "25.50")
    @field:Positive
    val precoUnitario: BigDecimal
)