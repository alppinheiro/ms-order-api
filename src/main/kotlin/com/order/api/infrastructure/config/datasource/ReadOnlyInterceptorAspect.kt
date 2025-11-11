package com.order.api.infrastructure.config.datasource

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Aspect
@Component
@Order(1)
class ReadOnlyInterceptorAspect {

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional) && execution(* *(..))")
    fun transactionalMethods() {}

    @Around("transactionalMethods() && @annotation(tx)")
    fun around(joinPoint: ProceedingJoinPoint, tx: Transactional): Any? {
        return try {
            if (tx.readOnly) {
                DataSourceContextHolder.set(DataSourceType.READ)
            } else {
                DataSourceContextHolder.set(DataSourceType.WRITE)
            }
            joinPoint.proceed()
        } finally {
            DataSourceContextHolder.clear()
        }
    }
}

