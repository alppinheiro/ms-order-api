package com.order.api.infrastructure.config

import com.order.api.infrastructure.adapter.output.PedidoRepositoryInMemory
import com.order.api.application.usecase.ConsultarPedidoUseCase
import com.order.api.application.usecase.CriarPedidoUseCase
import com.order.api.domain.port.output.PedidoRepository
import com.order.api.domain.service.ConsultarPedidoService
import com.order.api.domain.service.CriarPedidoService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeansConfiguration {
    @Bean
    fun pedidoRepository(): PedidoRepository = PedidoRepositoryInMemory()

    @Bean
    fun criarPedidoUseCase(pedidoRepository: PedidoRepository): CriarPedidoUseCase =
        CriarPedidoService(pedidoRepository)

    @Bean
    fun consultarPedidoUseCase(pedidoRepository: PedidoRepository): ConsultarPedidoUseCase =
        ConsultarPedidoService(pedidoRepository)
}