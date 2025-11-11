package com.order.api.infrastructure.adapter.output.repository.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "pedidos")
class PedidoEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "data_criacao")
    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "cliente_id")
    var cliente: ClienteEntity = ClienteEntity(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "endereco_entrega_id")
    var enderecoEntrega: EnderecoEntity = EnderecoEntity(),

    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], orphanRemoval = true)
    var itens: MutableList<ItemPedidoEntity> = mutableListOf(),

    @Column(name = "valor_total")
    var valorTotal: BigDecimal = java.math.BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    var status: StatusPedidoEntity = StatusPedidoEntity.CRIADO
)