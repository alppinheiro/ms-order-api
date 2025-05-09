package com.order.api.infrastructure.adapter.input.controller.request

import java.math.BigDecimal

data class ItemPedidoRequest(
    val produto: String,
    val quantidade: Int,
    val precoUnitario: BigDecimal
)