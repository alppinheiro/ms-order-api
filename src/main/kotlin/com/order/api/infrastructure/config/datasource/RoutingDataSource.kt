package com.order.api.infrastructure.config.datasource

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class RoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any? = DataSourceContextHolder.get()
}

