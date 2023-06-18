package com.janus.sensor_repository.adapters.sensorMessageProcessor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.janus.sensor_repository.domain.port.EventProcessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class SensorMessageProcessor: EventProcessor<SensorMessageEvent> {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @RabbitListener(queues = ["\${sensor_messages.processor.queue.name}"])
    override fun process(event: SensorMessageEvent) {
        logger.debug("operation=process, message='Received SensorMessageEvent {}'", event)
    }

    @Bean
    fun converter(): Jackson2JsonMessageConverter? {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        return Jackson2JsonMessageConverter(objectMapper)
    }

}