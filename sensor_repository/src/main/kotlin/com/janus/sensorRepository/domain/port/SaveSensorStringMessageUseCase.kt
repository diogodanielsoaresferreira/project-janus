package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorStringMessage

interface SaveSensorStringMessageUseCase {
    fun execute(sensorMessage: SensorStringMessage)
}