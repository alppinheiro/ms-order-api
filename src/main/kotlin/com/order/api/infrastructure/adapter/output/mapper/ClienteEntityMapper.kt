package com.order.api.infrastructure.adapter.output.mapper

import com.order.api.domain.model.Cliente
import com.order.api.infrastructure.adapter.output.repository.entity.ClienteEntity

class ClienteEntityMapper {
    fun toEntity(model: Cliente) = ClienteEntity(
        id = 0,
        nome = model.nome,
        email = model.email,
        cpf = model.cpf
    )

    fun toModel(entity: ClienteEntity) = Cliente(
        nome = entity.nome,
        email = entity.email,
        cpf = entity.cpf
    )
}