package com.order.api.infrastructure.config.datasource

enum class DataSourceType {
    READ, WRITE
}

object DataSourceContextHolder {
    private val context = ThreadLocal<DataSourceType>()

    fun set(type: DataSourceType) = context.set(type)
    fun get(): DataSourceType? = context.get()
    fun clear() = context.remove()
}

