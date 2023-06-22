package com.janus.sensorRepository

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableRabbit
@SpringBootApplication
class SensorRepositoryApplication

fun main(args: Array<String>) {
	runApplication<SensorRepositoryApplication>(*args)
}
