package com.order.api.infrastructure.adapter.input.controller.request

import java.math.BigDecimal
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class ItemPedidoRequest(
    @field:NotBlank
    val produto: String,

    @field:Positive
    val quantidade: Int,

    @field:Positive
    val precoUnitario: BigDecimal
)