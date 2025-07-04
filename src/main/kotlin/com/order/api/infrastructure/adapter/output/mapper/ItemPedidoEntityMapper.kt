package com.order.api.infrastructure.adapter.output.mapper

import com.order.api.domain.model.ItemPedido
import com.order.api.domain.model.Pedido
import com.order.api.infrastructure.adapter.output.repository.entity.ItemPedidoEntity

class ItemPedidoEntityMapper {
    fun toEntity(model: ItemPedido, pedido: Pedido) = ItemPedidoEntity(
        id = 0,
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