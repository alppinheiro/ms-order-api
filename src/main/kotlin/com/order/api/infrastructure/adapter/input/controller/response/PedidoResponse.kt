package com.order.api.infrastructure.adapter.input.controller.response

import com.order.api.domain.model.StatusPedido
import java.math.BigDecimal
import java.time.LocalDateTime
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Representação de um pedido")
data class PedidoResponse(
    @field:Schema(description = "Identificador do pedido", example = "1")
    val id: Long,

    @field:Schema(description = "Data de criação do pedido")
    val dataCriacao: LocalDateTime,

    @field:Schema(description = "Dados do cliente")
    val cliente: ClienteResponse,

    @field:Schema(description = "Endereço de entrega")
    val enderecoEntrega: EnderecoResponse,

    @field:Schema(description = "Itens do pedido")
    val itens: List<ItemPedidoResponse>,

    @field:Schema(description = "Valor total do pedido", example = "100.00")
    val valorTotal: BigDecimal,

    @field:Schema(description = "Status do pedido")
    val status: StatusPedido
)