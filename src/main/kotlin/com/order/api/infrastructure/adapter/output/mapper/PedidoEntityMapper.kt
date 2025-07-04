package com.order.api.infrastructure.adapter.output.mapper

import com.order.api.domain.model.Pedido
import com.order.api.domain.model.StatusPedido
import com.order.api.infrastructure.adapter.output.repository.entity.PedidoEntity
import com.order.api.infrastructure.adapter.output.repository.entity.StatusPedidoEntity

class PedidoEntityMapper(
    private val clienteMapper: ClienteEntityMapper = ClienteEntityMapper(),
    private val enderecoMapper: EnderecoEntityMapper = EnderecoEntityMapper(),
    private val itemMapper: ItemPedidoEntityMapper = ItemPedidoEntityMapper()
) {
    fun toEntity(model: Pedido): PedidoEntity {
        val clienteEntity = clienteMapper.toEntity(model.cliente)
        val enderecoEntity = enderecoMapper.toEntity(model.enderecoEntrega)
        val pedidoEntity = PedidoEntity(
            id = model.id,
            dataCriacao = model.dataCriacao,
            cliente = clienteEntity,
            enderecoEntrega = enderecoEntity,
            valorTotal = model.valorTotal,
            status = StatusPedidoEntity.valueOf(model.status.name)
        )
        val itensEntities = model.itens.map { itemMapper.toEntity(it, model) }
        return pedidoEntity.copy(itens = itensEntities)
    }

    fun toModel(entity: PedidoEntity) = Pedido(
        id = entity.id,
        dataCriacao = entity.dataCriacao,
        cliente = clienteMapper.toModel(entity.cliente),
        enderecoEntrega = enderecoMapper.toModel(entity.enderecoEntrega),
        itens = entity.itens.map { itemMapper.toModel(it) },
        valorTotal = entity.valorTotal,
        status = StatusPedido.valueOf(entity.status.name)
    )
}