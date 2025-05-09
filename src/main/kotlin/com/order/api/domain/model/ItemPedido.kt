package com.order.api.domain.model

import java.math.BigDecimal

data class ItemPedido(
    val produto: String,
    val quantidade: Int,
    val precoUnitario: BigDecimal
)