package com.order.api.domain.service

import com.order.api.domain.model.Pedido
import com.order.api.application.usecase.ConsultarPedidoUseCase
import com.order.api.domain.port.output.PedidoRepository
import java.util.UUID


class ConsultarPedidoService(
    private val pedidoRepository: PedidoRepository
) : ConsultarPedidoUseCase {

    override fun consultar(id: UUID): Pedido? {
        return pedidoRepository.buscarPorId(id)
    }
}