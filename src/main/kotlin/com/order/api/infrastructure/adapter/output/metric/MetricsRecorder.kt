package com.order.api.infrastructure.adapter.output.metric
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.springframework.stereotype.Component

@Component
class MetricsRecorder(private val meterRegistry: MeterRegistry) {

    fun registrarContador(nome: String, tags: Map<String, String>) {
        val tagList = tags.map { Tag.of(it.key, it.value) }
        val counter = meterRegistry.counter(nome, tagList)
        counter.increment()
    }

    fun registrarGauge(nome: String, valor: Double, tags: Map<String, String> = emptyMap()) {
        val gaugeHolder = PedidoMetricsHolder(valor)
        val tagList = tags.map { Tag.of(it.key, it.value) }
        meterRegistry.gauge(nome, tagList, gaugeHolder) { it.valor }
    }
}