package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorStringMessage
import com.janus.sensorRepository.domain.port.SaveSensorStringMessageUseCase
import com.janus.sensorRepository.domain.port.SensorStringMessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SaveSensorStringMessageUseCaseImpl(
    @Autowired private val repository: SensorStringMessageRepository
): SaveSensorStringMessageUseCase {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(sensorMessage: SensorStringMessage) {
        logger.debug("operation=save, message='saving SensorStringMessage {}'", sensorMessage)
        repository.save(sensorMessage)
    }
}