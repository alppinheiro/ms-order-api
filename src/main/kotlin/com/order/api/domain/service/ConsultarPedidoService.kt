package com.order.api.domain.service

import com.order.api.domain.model.Pedido
import com.order.api.application.usecase.ConsultarPedidoUseCase
import com.order.api.domain.port.output.PedidoRepository


class ConsultarPedidoService(
    private val pedidoRepository: PedidoRepository
) : ConsultarPedidoUseCase {

    override fun consultar(id: Long): Pedido? {
        return pedidoRepository.buscarPorId(id)
    }
}