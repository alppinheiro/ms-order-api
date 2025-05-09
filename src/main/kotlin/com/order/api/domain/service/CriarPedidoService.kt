package com.order.api.domain.service

import com.order.api.domain.model.Pedido
import com.order.api.application.usecase.CriarPedidoUseCase
import com.order.api.domain.port.output.PedidoRepository

class CriarPedidoService(
    private val pedidoRepository: PedidoRepository
) : CriarPedidoUseCase {

    override fun criar(pedido: Pedido): Pedido {
        return pedidoRepository.salvar(pedido)
    }
}