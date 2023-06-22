package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorStringMessage

interface SensorStringMessageRepository {
    fun save(entity: SensorStringMessage)
}