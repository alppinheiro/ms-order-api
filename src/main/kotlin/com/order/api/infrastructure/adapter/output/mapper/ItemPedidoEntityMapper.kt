package com.order.api.infrastructure.adapter.output.mapper

import com.order.api.domain.model.ItemPedido
import com.order.api.infrastructure.adapter.output.repository.entity.ItemPedidoEntity
import com.order.api.infrastructure.adapter.output.repository.entity.PedidoEntity

@org.springframework.stereotype.Component
class ItemPedidoEntityMapper {
    fun toEntity(model: ItemPedido, pedido: PedidoEntity) = ItemPedidoEntity(
        id = null,
        produto = model.produto,
        quantidade = model.quantidade,
        precoUnitario = model.precoUnitario,
        pedido = pedido
    )

    fun toModel(entity: ItemPedidoEntity) = ItemPedido(
        produto = entity.produto,
        quantidade = entity.quantidade,
        precoUnitario = entity.precoUnitario
    )
}