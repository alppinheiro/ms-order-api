package com.order.api.infrastructure.adapter.output.repository.impl

import com.order.api.domain.model.Pedido
import com.order.api.domain.port.output.PedidoRepository
import com.order.api.infrastructure.adapter.output.mapper.PedidoEntityMapper
import com.order.api.infrastructure.adapter.output.repository.PedidoJPARepository

class PedidoRepositoryImpl(
    private val pedidoJPARepository: PedidoJPARepository,
    private val pedidoEntityMapper: PedidoEntityMapper
): PedidoRepository {
    override fun salvar(pedido: Pedido): Pedido {
        val entity = pedidoEntityMapper.toEntity(pedido)
        val result = pedidoJPARepository.save(entity)
        return pedidoEntityMapper.toModel(result)
    }

    override fun buscarPorId(id: Long): Pedido? {
        val result = pedidoJPARepository.getReferenceById(id)
        return pedidoEntityMapper.toModel(result)
    }

}