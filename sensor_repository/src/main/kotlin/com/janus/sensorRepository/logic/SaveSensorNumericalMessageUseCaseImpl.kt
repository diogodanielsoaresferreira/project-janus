package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.port.SaveSensorNumericalMessageUseCase
import com.janus.sensorRepository.domain.port.SensorNumericalMessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SaveSensorNumericalMessageUseCaseImpl(
    @Autowired private val repository: SensorNumericalMessageRepository
): SaveSensorNumericalMessageUseCase {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(sensorMessage: SensorNumericalMessage) {
        logger.debug("operation=save, message='saving SensorNumericalMessage {}'", sensorMessage)
        repository.save(sensorMessage)
    }
}