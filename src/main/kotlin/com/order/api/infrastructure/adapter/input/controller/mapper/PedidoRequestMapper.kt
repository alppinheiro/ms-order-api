package com.order.api.infrastructure.adapter.input.controller.mapper

import com.order.api.infrastructure.adapter.input.controller.request.PedidoRequest
import com.order.api.domain.model.Cliente
import com.order.api.domain.model.Endereco
import com.order.api.domain.model.ItemPedido
import com.order.api.domain.model.Pedido

object PedidoRequestMapper {
    fun toDomain(request: PedidoRequest): Pedido {
        return Pedido(
            cliente = Cliente(
                nome = request.cliente.nome,
                email = request.cliente.email,
                cpf = request.cliente.cpf
            ),
            enderecoEntrega = Endereco(
                rua = request.enderecoEntrega.rua,
                numero = request.enderecoEntrega.numero,
                complemento = request.enderecoEntrega.complemento,
                bairro = request.enderecoEntrega.bairro,
                cidade = request.enderecoEntrega.cidade,
                estado = request.enderecoEntrega.estado,
                cep = request.enderecoEntrega.cep
            ),
            itens = request.itens.map {
                ItemPedido(
                    produto = it.produto,
                    quantidade = it.quantidade,
                    precoUnitario = it.precoUnitario
                )
            },
            valorTotal = request.itens.sumOf {
                it.precoUnitario.multiply(it.quantidade.toBigDecimal())
            }
        )
    }
}