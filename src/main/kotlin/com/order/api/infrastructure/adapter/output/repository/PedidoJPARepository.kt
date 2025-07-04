package com.order.api.infrastructure.adapter.output.repository

import com.order.api.infrastructure.adapter.output.repository.entity.PedidoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PedidoJPARepository: JpaRepository<PedidoEntity, Long>