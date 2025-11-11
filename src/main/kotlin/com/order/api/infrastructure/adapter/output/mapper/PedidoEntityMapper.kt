package com.order.api.infrastructure.adapter.output.mapper

import com.order.api.domain.model.Pedido
import com.order.api.domain.model.StatusPedido
import com.order.api.infrastructure.adapter.output.repository.entity.PedidoEntity
import com.order.api.infrastructure.adapter.output.repository.entity.StatusPedidoEntity

@org.springframework.stereotype.Component
class PedidoEntityMapper(
    private val clienteMapper: ClienteEntityMapper,
    private val enderecoMapper: EnderecoEntityMapper,
    private val itemMapper: ItemPedidoEntityMapper
) {
    fun toEntity(model: Pedido): PedidoEntity {
        val clienteEntity = clienteMapper.toEntity(model.cliente)
        val enderecoEntity = enderecoMapper.toEntity(model.enderecoEntrega)
        // create PedidoEntity with empty itens mutable list and ensure id is null for new models
        val pedidoEntity = PedidoEntity(
            id = if (model.id <= 0L) null else model.id,
            dataCriacao = model.dataCriacao,
            cliente = clienteEntity,
            enderecoEntrega = enderecoEntity,
            itens = mutableListOf(),
            valorTotal = model.valorTotal,
            status = StatusPedidoEntity.valueOf(model.status.name)
        )
        val itensEntities = model.itens.map { itemMapper.toEntity(it, pedidoEntity) }
        // set back-reference and add to pedidoEntity.itens
        pedidoEntity.itens.clear()
        pedidoEntity.itens.addAll(itensEntities)
        return pedidoEntity
    }

    fun toModel(entity: PedidoEntity) = Pedido(
        id = entity.id ?: 0L,
        dataCriacao = entity.dataCriacao,
        cliente = clienteMapper.toModel(entity.cliente),
        enderecoEntrega = enderecoMapper.toModel(entity.enderecoEntrega),
        itens = entity.itens.map { itemMapper.toModel(it) },
        valorTotal = entity.valorTotal,
        status = StatusPedido.valueOf(entity.status.name)
    )
}