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

// Anotações OpenAPI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema

@Tag(name = "Orders", description = "Operations related to orders")
@RestController
@RequestMapping("/orders")
class PedidoController(
    private val criarPedidoUseCase: CriarPedidoUseCase,
    private val consultarPedidoUseCase: ConsultarPedidoUseCase
) {
    private val logger = KotlinLogging.logger {}

    @Counted(value = "api.pedido.controller.count", description = "Contador de chamadas ao endpoint hello")
    @Timed(value = "api.pedido.controller.timer", description = "Tempo de resposta do endpoint criar")
    @Operation(summary = "Create order", description = "Creates a new order")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Order created",
                content = [Content(schema = Schema(implementation = PedidoResponse::class))]
            )
        ]
    )
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
    @Operation(summary = "Get order", description = "Retrieve an order by id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Order found",
                content = [Content(schema = Schema(implementation = PedidoResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Order not found")
        ]
    )
    @GetMapping("/{id}")
    fun consultar(@PathVariable id: Long): ResponseEntity<PedidoResponse> {
        logger.info { "Chamando endpoint /consultar/${id}" }
        val pedido = consultarPedidoUseCase.consultar(id)
        return pedido?.let {
            logger.info { "Fim da chamanda do endpoint /cunsultar" }
            ResponseEntity.ok(PedidoResponseMapper.fromDomain(it))
        } ?: ResponseEntity.notFound().build()
    }
}