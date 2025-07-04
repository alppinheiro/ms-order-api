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
    val id: Long = 0,
    val rua: String,
    val numero: String,
    val complemento: String?,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val cep: String
)