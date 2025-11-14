package com.order.api.domain.port.output

import com.order.api.domain.model.Pedido

interface PedidoRepository {
    fun salvar(pedido: Pedido): Pedido
    fun buscarPorId(id: Long): Pedido?
}
