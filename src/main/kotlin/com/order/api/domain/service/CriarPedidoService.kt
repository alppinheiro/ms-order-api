package com.order.api.domain.service

import com.order.api.domain.model.Pedido
import com.order.api.application.usecase.CriarPedidoUseCase
import com.order.api.domain.port.output.PedidoRepository
import com.order.api.infrastructure.adapter.output.metric.MetricsRecorder
import mu.KotlinLogging
import org.springframework.transaction.annotation.Transactional

open class CriarPedidoService(
    private val pedidoRepository: PedidoRepository,
    private val metrics: MetricsRecorder
) : CriarPedidoUseCase {

    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun criar(pedido: Pedido): Pedido {
        logger.info { "Camada service: CriarPedidoUseCase" }
        return pedidoRepository.salvar(pedido).also {
            metrics.registrarContador("pedido.quantidade", mapOf(
                "idPedido" to it.id.toString(),
                "status" to it.status.toString(),
                "valorTotal" to it.valorTotal.toString()))
        }
    }
}