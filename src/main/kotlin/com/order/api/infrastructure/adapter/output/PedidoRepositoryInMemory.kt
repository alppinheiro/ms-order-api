package com.order.api.infrastructure.adapter.output

import com.order.api.domain.model.Pedido
import com.order.api.domain.port.output.PedidoRepository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class PedidoRepositoryInMemory : PedidoRepository {

    private val pedidos = ConcurrentHashMap<UUID, Pedido>()

    override fun salvar(pedido: Pedido): Pedido {
        pedidos[pedido.id] = pedido
        return pedido
    }

    override fun buscarPorId(id: UUID): Pedido? {
        return pedidos[id]
    }
}