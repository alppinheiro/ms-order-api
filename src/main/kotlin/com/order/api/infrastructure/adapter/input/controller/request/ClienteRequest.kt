package com.order.api.infrastructure.adapter.input.controller.request

data class ClienteRequest(
    val nome: String,
    val email: String,
    val cpf: String
)