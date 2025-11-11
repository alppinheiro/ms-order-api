package com.order.api.infrastructure.adapter.input.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

@Schema(description = "Payload para criação de pedido")
data class PedidoRequest(
    @field:Schema(description = "Dados do cliente")
    @field:Valid
    val cliente: ClienteRequest,

    @field:Schema(description = "Endereço de entrega")
    @field:Valid
    val enderecoEntrega: EnderecoRequest,

    @field:Schema(description = "Itens do pedido")
    @field:NotEmpty
    @field:Valid
    val itens: List<ItemPedidoRequest>
)
