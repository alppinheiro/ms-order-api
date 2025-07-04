package com.order.api.application.usecase

import com.order.api.domain.model.Pedido
import java.util.UUID

interface ConsultarPedidoUseCase {
    fun consultar(id: Long): Pedido?
}