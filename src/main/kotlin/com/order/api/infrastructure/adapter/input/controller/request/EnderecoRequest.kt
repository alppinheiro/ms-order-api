package com.order.api.infrastructure.adapter.input.controller.request

data class EnderecoRequest(
    val rua: String,
    val numero: String,
    val complemento: String?,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val cep: String
)