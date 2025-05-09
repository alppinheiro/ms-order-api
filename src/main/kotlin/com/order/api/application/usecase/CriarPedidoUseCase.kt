package com.order.api.application.usecase

import com.order.api.domain.model.Pedido

interface CriarPedidoUseCase {
    fun criar(pedido: Pedido): Pedido
}