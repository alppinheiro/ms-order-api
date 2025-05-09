package com.order.api.infrastructure.adapter.input.controller.mapper

import com.order.api.infrastructure.adapter.input.controller.response.ClienteResponse
import com.order.api.infrastructure.adapter.input.controller.response.EnderecoResponse
import com.order.api.infrastructure.adapter.input.controller.response.ItemPedidoResponse
import com.order.api.infrastructure.adapter.input.controller.response.PedidoResponse
import com.order.api.domain.model.Pedido

object PedidoResponseMapper {
    fun fromDomain(pedido: Pedido): PedidoResponse {
        return PedidoResponse(
            id = pedido.id,
            dataCriacao = pedido.dataCriacao,
            cliente = ClienteResponse(
                nome = pedido.cliente.nome,
                email = pedido.cliente.email,
                cpf = pedido.cliente.cpf
            ),
            enderecoEntrega = EnderecoResponse(
                rua = pedido.enderecoEntrega.rua,
                numero = pedido.enderecoEntrega.numero,
                complemento = pedido.enderecoEntrega.complemento,
                bairro = pedido.enderecoEntrega.bairro,
                cidade = pedido.enderecoEntrega.cidade,
                estado = pedido.enderecoEntrega.estado,
                cep = pedido.enderecoEntrega.cep
            ),
            itens = pedido.itens.map {
                ItemPedidoResponse(
                    produto = it.produto,
                    quantidade = it.quantidade,
                    precoUnitario = it.precoUnitario
                )
            },
            valorTotal = pedido.valorTotal,
            status = pedido.status
        )
    }
}