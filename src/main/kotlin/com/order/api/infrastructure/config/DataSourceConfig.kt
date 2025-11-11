package com.order.api.infrastructure.config

import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource
import com.order.api.infrastructure.config.datasource.DataSourceType
import com.order.api.infrastructure.config.datasource.RoutingDataSource

@Configuration
class DataSourceConfig {
    @Bean(name = ["writeDataSourceProperties"])
    @ConfigurationProperties("spring.datasource.write")
    fun writeDataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean(name = ["readDataSourceProperties"])
    @ConfigurationProperties("spring.datasource.read")
    fun readDataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean(name = ["writeDataSource"])
    fun writeDataSource(
        @Qualifier("writeDataSourceProperties") props: DataSourceProperties
    ): DataSource = props.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()

    @Bean(name = ["readDataSource"])
    fun readDataSource(
        @Qualifier("readDataSourceProperties") props: DataSourceProperties
    ): DataSource = props.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()

    // DataSource primário de routing: delega entre WRITE e READ
    @Bean
    @Primary
    fun dataSource(
        @Qualifier("writeDataSource") write: DataSource,
        @Qualifier("readDataSource") read: DataSource
    ): DataSource {
        val routing = RoutingDataSource()
        val target: MutableMap<Any, Any> = mutableMapOf()
        target[DataSourceType.WRITE] = write
        target[DataSourceType.READ] = read
        routing.setTargetDataSources(target)
        routing.setDefaultTargetDataSource(write)
        routing.afterPropertiesSet()
        return routing
    }

    @Bean(initMethod = "migrate")
    fun flyway(
        @Qualifier("writeDataSource") dataSource: DataSource
    ): Flyway = Flyway.configure()
        .locations("classpath:db/migration")
        .dataSource(dataSource)
        .load()
}