package com.order.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MsOrderApiApplication

fun main(args: Array<String>) {
	runApplication<MsOrderApiApplication>(*args)
}
