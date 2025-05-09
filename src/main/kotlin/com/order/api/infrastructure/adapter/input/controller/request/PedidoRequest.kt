package com.order.api.infrastructure.adapter.input.controller.request

data class PedidoRequest(
    val cliente: ClienteRequest,
    val enderecoEntrega: EnderecoRequest,
    val itens: List<ItemPedidoRequest>
)
