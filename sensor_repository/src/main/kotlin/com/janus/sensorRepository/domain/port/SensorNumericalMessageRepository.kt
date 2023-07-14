package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage

interface SensorNumericalMessageRepository {
    fun save(entity: SensorNumericalMessage)
}