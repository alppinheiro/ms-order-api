package com.order.api.infrastructure.adapter.input.controller.response

import com.order.api.domain.model.StatusPedido
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class PedidoResponse(
    val id: Long,
    val dataCriacao: LocalDateTime,
    val cliente: ClienteResponse,
    val enderecoEntrega: EnderecoResponse,
    val itens: List<ItemPedidoResponse>,
    val valorTotal: BigDecimal,
    val status: StatusPedido
)