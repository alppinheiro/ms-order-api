package com.order.api.infrastructure.adapter.input.controller.response

import java.math.BigDecimal
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Item do pedido - response")
data class ItemPedidoResponse(
    @field:Schema(description = "Nome do produto", example = "Caneca")
    val produto: String,

    @field:Schema(description = "Quantidade", example = "2")
    val quantidade: Int,

    @field:Schema(description = "Preço unitário", example = "25.50")
    val precoUnitario: BigDecimal
)