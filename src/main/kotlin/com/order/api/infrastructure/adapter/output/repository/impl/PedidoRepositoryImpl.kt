package com.order.api.infrastructure.adapter.output.repository.impl

import com.order.api.domain.model.Pedido
import com.order.api.domain.port.output.PedidoRepository
import com.order.api.infrastructure.adapter.output.repository.PedidoJPARepository
import java.util.UUID

class PedidoRepositoryImpl(
    private val pedidoJPARepository: PedidoJPARepository
): PedidoRepository {
    override fun salvar(pedido: Pedido): Pedido {
        pedidoJPARepository.save()
    }

    override fun buscarPorId(id: UUID): Pedido? {
        pedidoJPARepository.findById(id)
    }

}