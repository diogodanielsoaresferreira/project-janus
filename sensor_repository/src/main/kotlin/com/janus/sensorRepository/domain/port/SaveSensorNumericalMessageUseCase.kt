package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage

interface SaveSensorNumericalMessageUseCase {
    fun execute(sensorMessage: SensorNumericalMessage)
}