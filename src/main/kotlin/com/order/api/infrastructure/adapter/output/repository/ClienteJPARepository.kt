package com.order.api.infrastructure.adapter.output.repository

import com.order.api.infrastructure.adapter.output.repository.entity.ClienteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteJPARepository: JpaRepository<ClienteEntity, Long> {
    fun findByEmail(email: String): ClienteEntity?
}

