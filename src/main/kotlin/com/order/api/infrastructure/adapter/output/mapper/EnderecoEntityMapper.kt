package com.order.api.infrastructure.adapter.output.mapper

import com.order.api.domain.model.Endereco
import com.order.api.infrastructure.adapter.output.repository.entity.EnderecoEntity

@org.springframework.stereotype.Component
class EnderecoEntityMapper {
    fun toEntity(model: Endereco) = EnderecoEntity(
        id = null,
        rua = model.rua,
        numero = model.numero,
        complemento = model.complemento,
        bairro = model.bairro,
        cidade = model.cidade,
        estado = model.estado,
        cep = model.cep
    )

    fun toModel(entity: EnderecoEntity) = Endereco(
        rua = entity.rua,
        numero = entity.numero,
        complemento = entity.complemento,
        bairro = entity.bairro,
        cidade = entity.cidade,
        estado = entity.estado,
        cep = entity.cep
    )
}