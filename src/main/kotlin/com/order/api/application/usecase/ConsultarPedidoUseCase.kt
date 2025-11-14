package com.order.api.application.usecase

import com.order.api.domain.model.Pedido

interface ConsultarPedidoUseCase {
    fun consultar(id: Long): Pedido?
}
