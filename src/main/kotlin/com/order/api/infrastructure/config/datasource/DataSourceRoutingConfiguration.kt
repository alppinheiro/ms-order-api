package com.order.api.infrastructure.config.datasource

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import javax.sql.DataSource

class RoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any? = DataSourceContextHolder.get()
}

@Configuration
class DataSourceRoutingConfiguration {

    @Bean(name = ["writeDataSourceProperties"])
    @ConfigurationProperties("spring.datasource.write")
    fun writeDataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean(name = ["readDataSourceProperties"])
    @ConfigurationProperties("spring.datasource.read")
    fun readDataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean(name = ["writeDataSource"])
    fun writeDataSource(@Qualifier("writeDataSourceProperties") props: DataSourceProperties): DataSource {
        val ds = props.initializeDataSourceBuilder().type(HikariDataSource::class.java).build() as HikariDataSource
        return ds
    }

    @Bean(name = ["readDataSource"])
    fun readDataSource(@Qualifier("readDataSourceProperties") props: DataSourceProperties): DataSource {
        val ds = props.initializeDataSourceBuilder().type(HikariDataSource::class.java).build() as HikariDataSource
        return ds
    }

    @Bean
    @Primary
    fun dataSource(@Qualifier("writeDataSource") write: DataSource, @Qualifier("readDataSource") read: DataSource): DataSource {
        val routing = RoutingDataSource()
        val target = mapOf(DataSourceType.WRITE to write, DataSourceType.READ to read)
        routing.setTargetDataSources(target)
        routing.setDefaultTargetDataSource(write)
        routing.afterPropertiesSet()
        return routing
    }
}

