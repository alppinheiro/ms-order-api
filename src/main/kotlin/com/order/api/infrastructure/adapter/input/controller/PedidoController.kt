package com.order.api.infrastructure.adapter.input.controller

import com.order.api.infrastructure.adapter.input.controller.mapper.PedidoRequestMapper
import com.order.api.infrastructure.adapter.input.controller.mapper.PedidoResponseMapper
import com.order.api.infrastructure.adapter.input.controller.request.PedidoRequest
import com.order.api.infrastructure.adapter.input.controller.response.PedidoResponse
import com.order.api.application.usecase.ConsultarPedidoUseCase
import com.order.api.application.usecase.CriarPedidoUseCase
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/orders")
class PedidoController(
    private val criarPedidoUseCase: CriarPedidoUseCase,
    private val consultarPedidoUseCase: ConsultarPedidoUseCase
) {
    private val logger = KotlinLogging.logger {}

    @Counted(value = "api.pedido.controller.count", description = "Contador de chamadas ao endpoint hello")
    @Timed(value = "api.pedido.controller.timer", description = "Tempo de resposta do endpoint criar")
    @PostMapping
    fun criar(@RequestBody request: PedidoRequest): ResponseEntity<PedidoResponse> {
        logger.info { "Chamando endpoint /criar" }
        val pedidoCriado = criarPedidoUseCase.criar(PedidoRequestMapper.toDomain(request))
        val response = PedidoResponseMapper.fromDomain(pedidoCriado)
        return ResponseEntity.ok(response).also {
            logger.info { "Fim da chamanda do endpoint /criar" }
        }
    }

    @Counted(value = "api.pedido.controller.count", description = "Contador de chamadas ao endpoint consultar")
    @Timed(value = "api.pedido.controller.timer", description = "Tempo de resposta do endpoint consultar")
    @GetMapping("/{id}")
    fun consultar(@PathVariable id: UUID): ResponseEntity<PedidoResponse> {
        logger.info { "Chamando endpoint /consultar/${id}" }
        val pedido = consultarPedidoUseCase.consultar(id)
        return pedido?.let {
            logger.info { "Fim da chamanda do endpoint /cunsultar" }
            ResponseEntity.ok(PedidoResponseMapper.fromDomain(it))
        } ?: ResponseEntity.notFound().build()
    }
}