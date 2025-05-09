package com.order.api.infrastructure.adapter.input.controller

import com.order.api.infrastructure.adapter.input.controller.mapper.PedidoRequestMapper
import com.order.api.infrastructure.adapter.input.controller.mapper.PedidoResponseMapper
import com.order.api.infrastructure.adapter.input.controller.request.PedidoRequest
import com.order.api.infrastructure.adapter.input.controller.response.PedidoResponse
import com.order.api.application.usecase.ConsultarPedidoUseCase
import com.order.api.application.usecase.CriarPedidoUseCase
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
    @PostMapping
    fun criar(@RequestBody request: PedidoRequest): ResponseEntity<PedidoResponse> {
        val pedidoCriado = criarPedidoUseCase.criar(PedidoRequestMapper.toDomain(request))
        val response = PedidoResponseMapper.fromDomain(pedidoCriado)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun consultar(@PathVariable id: UUID): ResponseEntity<PedidoResponse> {
        val pedido = consultarPedidoUseCase.consultar(id)
        return pedido?.let {
            ResponseEntity.ok(PedidoResponseMapper.fromDomain(it))
        } ?: ResponseEntity.notFound().build()
    }
}