package com.order.api.infrastructure.adapter.output.repository

import com.order.api.domain.model.Pedido
import com.order.api.domain.port.output.PedidoRepository
import mu.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@org.springframework.stereotype.Repository
@org.springframework.context.annotation.Profile("inmemory", "default")
class PedidoRepositoryInMemory : PedidoRepository {

    private val logger = KotlinLogging.logger {}
    private val pedidos = ConcurrentHashMap<Long, Pedido>()
    private val idGenerator = AtomicLong(1)

    override fun salvar(pedido: Pedido): Pedido {
        logger.info { "Camada repository: metodo salvar" }
        val pedidoParaSalvar = if (pedido.id <= 0L) {
            val newId = idGenerator.getAndIncrement()
            pedido.copy(id = newId)
        } else pedido
        pedidos[pedidoParaSalvar.id] = pedidoParaSalvar
        logger.info { "pedido salvo: ${pedidos[pedidoParaSalvar.id]}" }
        return pedidoParaSalvar
    }

    override fun buscarPorId(id: Long): Pedido? {
        logger.info { "Camada repository: metodo buscarPorId. Pedido: ${pedidos[id]}" }
        return pedidos[id]
    }
}