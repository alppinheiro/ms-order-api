package com.order.api.infrastructure.adapter.input.controller.response

import java.math.BigDecimal

data class ItemPedidoResponse(
    val produto: String,
    val quantidade: Int,
    val precoUnitario: BigDecimal
)