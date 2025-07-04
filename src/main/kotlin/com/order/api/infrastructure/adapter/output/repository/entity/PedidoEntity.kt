package com.order.api.infrastructure.adapter.output.repository.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "pedidos")
data class Pedido(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "data_criacao")
    val dataCriacao: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "cliente_id")
    val cliente: ClienteEntity,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "endereco_entrega_id")
    val enderecoEntrega: EnderecoEntity,

    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], orphanRemoval = true)
    val itens: List<ItemPedidoEntity> = listOf(),

    @Column(name = "valor_total")
    val valorTotal: BigDecimal,

    @Enumerated(EnumType.STRING)
    val status: StatusPedidoEntity = StatusPedidoEntity.CRIADO
)