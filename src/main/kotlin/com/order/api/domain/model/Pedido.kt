package com.order.api.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Pedido(
    val id: Long = 0L,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val cliente: Cliente,
    val enderecoEntrega: Endereco,
    val itens: List<ItemPedido>,
    val valorTotal: BigDecimal,
    val status: StatusPedido = StatusPedido.CRIADO
)
