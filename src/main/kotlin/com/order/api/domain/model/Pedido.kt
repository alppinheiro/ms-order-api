package com.order.api.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Pedido(
    val id: UUID = UUID.randomUUID(),
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val cliente: Cliente,
    val enderecoEntrega: Endereco,
    val itens: List<ItemPedido>,
    val valorTotal: BigDecimal,
    val status: StatusPedido = StatusPedido.CRIADO
)
