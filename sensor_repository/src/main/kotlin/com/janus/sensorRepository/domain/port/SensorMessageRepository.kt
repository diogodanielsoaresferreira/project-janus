package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage

interface SensorMessageRepository {
    fun save(entity: SensorNumericalMessage)
    fun save(entity: SensorStringMessage)
}