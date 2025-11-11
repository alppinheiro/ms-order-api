package com.order.api.infrastructure.adapter.output.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "enderecos")
data class EnderecoEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var rua: String = "",
    var numero: String = "",
    var complemento: String? = null,
    var bairro: String = "",
    var cidade: String = "",
    var estado: String = "",
    var cep: String = ""
)