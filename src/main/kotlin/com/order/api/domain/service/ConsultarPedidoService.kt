package com.order.api.domain.service

import com.order.api.domain.model.Pedido
import com.order.api.application.usecase.ConsultarPedidoUseCase
import com.order.api.domain.port.output.PedidoRepository
import org.springframework.transaction.annotation.Transactional

open class ConsultarPedidoService(
    private val pedidoRepository: PedidoRepository
) : ConsultarPedidoUseCase {

    @Transactional(readOnly = true)
    override fun consultar(id: Long): Pedido? {
        return pedidoRepository.buscarPorId(id)
    }
}