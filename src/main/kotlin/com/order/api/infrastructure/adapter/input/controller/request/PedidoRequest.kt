package com.order.api.infrastructure.adapter.input.controller.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class PedidoRequest(
    @field:Valid
    val cliente: ClienteRequest,

    @field:Valid
    val enderecoEntrega: EnderecoRequest,

    @field:NotEmpty
    @field:Valid
    val itens: List<ItemPedidoRequest>
)
