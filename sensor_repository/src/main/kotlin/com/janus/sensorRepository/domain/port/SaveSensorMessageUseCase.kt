package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage

interface SaveSensorMessageUseCase {
    fun execute(sensorMessage: SensorNumericalMessage)
    fun execute(sensorMessage: SensorStringMessage)
}