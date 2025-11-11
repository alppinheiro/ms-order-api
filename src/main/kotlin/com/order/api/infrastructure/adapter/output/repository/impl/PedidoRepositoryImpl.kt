package com.order.api.infrastructure.adapter.output.repository.impl

import com.order.api.domain.model.Pedido
import com.order.api.domain.port.output.PedidoRepository
import com.order.api.infrastructure.adapter.output.mapper.PedidoEntityMapper
import com.order.api.infrastructure.adapter.output.repository.PedidoJPARepository
import com.order.api.infrastructure.adapter.output.repository.ClienteJPARepository
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import jakarta.persistence.EntityManager
import org.springframework.dao.DataIntegrityViolationException

@Repository
@org.springframework.context.annotation.Profile("db")
class PedidoRepositoryImpl(
    private val pedidoJPARepository: PedidoJPARepository,
    private val clienteJPARepository: ClienteJPARepository,
    private val pedidoEntityMapper: PedidoEntityMapper,
    private val entityManager: EntityManager
): PedidoRepository {

    private val logger = KotlinLogging.logger {}

    override fun salvar(pedido: Pedido): Pedido {
        val entity = pedidoEntityMapper.toEntity(pedido)

        // If somehow id was set to 0 (domain defaults use 0L), normalize to null so JPA treats as new
        if (entity.id != null && entity.id == 0L) {
            logger.warn { "PedidoEntity tinha id == 0 antes do save; forçando id para null para tratar como novo" }
            entity.id = null
        }

        // Resolve cliente: if cliente has no id, try to reuse existing by email
        val clienteEntity = entity.cliente
        if (clienteEntity.id == null) {
            val existing = clienteJPARepository.findByEmail(clienteEntity.email)
            if (existing != null && existing.id != null && existing.id!! > 0L) {
                logger.debug { "Encontrado Cliente existente por email=${existing.email}, usando referência gerenciada id=${existing.id}" }
                entity.cliente = entityManager.getReference(com.order.api.infrastructure.adapter.output.repository.entity.ClienteEntity::class.java, existing.id!!)
            }
        } else {
            val cid = clienteEntity.id
            if (cid != null && cid > 0L) {
                logger.debug { "Substituindo ClienteEntity destacado (id=$cid) por referência gerenciada" }
                entity.cliente = entityManager.getReference(com.order.api.infrastructure.adapter.output.repository.entity.ClienteEntity::class.java, cid)
            }
        }

        // Endereco is non-null in entity class; normalize and replace only when id is present and valid
        val endereco = entity.enderecoEntrega
        val eid = endereco.id
        if (eid != null && eid > 0L) {
            logger.debug { "Substituindo EnderecoEntity destacado (id=$eid) por referência gerenciada" }
            entity.enderecoEntrega = entityManager.getReference(com.order.api.infrastructure.adapter.output.repository.entity.EnderecoEntity::class.java, eid)
        }

        logger.debug { "Salvando PedidoEntity pré-save: id=${entity.id}, itens=${entity.itens.map { it.id }}" }

        try {
            val result = pedidoJPARepository.saveAndFlush(entity)
            logger.debug { "PedidoEntity pós-save: id=${result.id}, itens=${result.itens.map { it.id }}" }
            return pedidoEntityMapper.toModel(result)
        } catch (ex: DataIntegrityViolationException) {
            logger.warn(ex) { "Violação de integridade ao salvar; tentando recuperar se for cliente duplicado" }
            val msg = ex.rootCause?.message ?: ""
            if (msg.contains("clientes_email_key") || msg.contains("clientes_email_idx") || msg.contains("clientes_email")) {
                val existing = clienteJPARepository.findByEmail(clienteEntity.email)
                if (existing != null && existing.id != null && existing.id!! > 0L) {
                    logger.debug { "Após conflito, encontrado ClienteEntity id=${existing.id}, tentando salvar novamente" }
                    entity.cliente = entityManager.getReference(com.order.api.infrastructure.adapter.output.repository.entity.ClienteEntity::class.java, existing.id!!)
                    val retry = pedidoJPARepository.saveAndFlush(entity)
                    return pedidoEntityMapper.toModel(retry)
                }
            }
            throw ex
        }
    }

    override fun buscarPorId(id: Long): Pedido? {
        val opt = pedidoJPARepository.findById(id)
        return if (opt.isPresent) pedidoEntityMapper.toModel(opt.get()) else null
    }

}