package com.janus.sensorRepository.adapters.sensorMessageProcessor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.SensorMessageEvent
import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.toSensorNumericalMessage
import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.toSensorStringMessage
import com.janus.sensorRepository.domain.port.EventProcessor
import com.janus.sensorRepository.domain.port.SaveSensorMessageUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.lang.RuntimeException


@Component
class SensorMessageProcessor(
    @Autowired private val saveSensorMessageUseCase: SaveSensorMessageUseCase
): EventProcessor<SensorMessageEvent> {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @RabbitListener(queues = ["\${sensor_messages.processor.queue.name}"])
    override fun process(event: SensorMessageEvent) {
        logger.debug("operation=process, message='received SensorMessageEvent {}'", event)
        when(event.valueType.lowercase().trim()) {
            "string" -> processSensorMessageEventStringValueType(event)
            "numerical" -> processSensorMessageEventNumericalValueType(event)
            else -> {
                logger.error("operation=process, message='error processing SensorMessageEvent $event', " +
                        "cause='unknown valueType ${event.valueType}'")
                throw SensorMessageEventProcessingError("unknown valueType ${event.valueType}")
            }
        }
    }

    private fun processSensorMessageEventStringValueType(event: SensorMessageEvent) {
        logger.debug("operation=processSensorMessageEventStringValueType, message='processing SensorMessageEvent {} as SensorStringMessage'", event)
        saveSensorMessageUseCase.execute(event.toSensorStringMessage())
    }

    private fun processSensorMessageEventNumericalValueType(event: SensorMessageEvent) {
        logger.debug("operation=processSensorMessageEventNumericalValueType, message='processing SensorMessageEvent {} as SensorNumericalMessage'", event)
        saveSensorMessageUseCase.execute(event.toSensorNumericalMessage())
    }

    @Bean
    fun converter(): Jackson2JsonMessageConverter? {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        return Jackson2JsonMessageConverter(objectMapper)
    }

}

class SensorMessageEventProcessingError(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)