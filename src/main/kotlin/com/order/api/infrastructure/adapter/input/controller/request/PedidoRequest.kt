package com.order.api.infrastructure.adapter.input.controller.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Payload para criação de pedido")
data class PedidoRequest(
    @field:Schema(description = "Dados do cliente")
    val cliente: ClienteRequest,

    @field:Schema(description = "Endereço de entrega")
    val enderecoEntrega: EnderecoRequest,

    @field:Schema(description = "Itens do pedido")
    val itens: List<ItemPedidoRequest>
)
