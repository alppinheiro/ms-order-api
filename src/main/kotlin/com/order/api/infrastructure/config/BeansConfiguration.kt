package com.order.api.infrastructure.config

import com.order.api.application.usecase.ConsultarPedidoUseCase
import com.order.api.application.usecase.CriarPedidoUseCase
import com.order.api.domain.port.output.PedidoRepository
import com.order.api.domain.service.ConsultarPedidoService
import com.order.api.domain.service.CriarPedidoService
import com.order.api.infrastructure.adapter.output.metric.MetricsRecorder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeansConfiguration {
    // A criação do PedidoRepository era feita manualmente aqui retornando a implementação em memória.
    // Para delegar para profiles, removemos esse factory. As implementações estão anotadas com
    // @Repository e @Profile("inmemory") ou @Profile("db") e serão registradas automaticamente.

    @Bean
    fun criarPedidoUseCase(pedidoRepository: PedidoRepository, metricsRecorder: MetricsRecorder): CriarPedidoUseCase =
        CriarPedidoService(pedidoRepository, metricsRecorder)

    @Bean
    fun consultarPedidoUseCase(pedidoRepository: PedidoRepository): ConsultarPedidoUseCase =
        ConsultarPedidoService(pedidoRepository)
}