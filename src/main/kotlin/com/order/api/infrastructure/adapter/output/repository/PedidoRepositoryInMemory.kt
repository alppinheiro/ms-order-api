package com.order.api.infrastructure.adapter.output.repository

import com.order.api.domain.model.Pedido
import com.order.api.domain.port.output.PedidoRepository
import mu.KotlinLogging
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class PedidoRepositoryInMemory : PedidoRepository {

    private val logger = KotlinLogging.logger {}
    private val pedidos = ConcurrentHashMap<UUID, Pedido>()

    override fun salvar(pedido: Pedido): Pedido {
        logger.info { "Camada repository: metodo salvar" }
        pedidos[pedido.id] = pedido
        logger.info { "pedido salvo: ${pedidos[pedido.id]}" }
        return pedido
    }

    override fun buscarPorId(id: UUID): Pedido? {
        logger.info { "Camada repository: metodo buscarPorId. Pedido: ${pedidos[id]}" }
        return pedidos[id]
    }
}