package com.janus.sensor_repository.adapters.sensor_messages

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.janus.sensor_repository.domain.entity.SensorMessageEvent
import com.janus.sensor_repository.domain.port.EventProcessor
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class SensorMessagesProcessor: EventProcessor<SensorMessageEvent> {
    @RabbitListener(queues = ["\${sensor_messages.processor.queue.name}"])
    override fun process(event: SensorMessageEvent) {
        println("Message $event")
    }

    @Bean
    fun converter(): Jackson2JsonMessageConverter? {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        return Jackson2JsonMessageConverter(objectMapper)
    }

}