package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage
import com.janus.sensorRepository.domain.port.SaveSensorMessageUseCase
import com.janus.sensorRepository.domain.port.SensorMessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SaveSensorMessageUseCaseImpl(
    @Autowired private val repository: SensorMessageRepository
): SaveSensorMessageUseCase {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(sensorMessage: SensorNumericalMessage) {
        logger.debug("operation=save, message='saving SensorNumericalMessage {}'", sensorMessage)
        repository.save(sensorMessage)
    }

    override fun execute(sensorMessage: SensorStringMessage) {
        logger.debug("operation=save, message='saving SensorStringMessage {}'", sensorMessage)
        repository.save(sensorMessage)
    }
}