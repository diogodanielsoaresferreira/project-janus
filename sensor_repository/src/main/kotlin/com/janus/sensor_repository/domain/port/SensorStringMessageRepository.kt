package com.janus.sensor_repository.domain.port

import com.janus.sensor_repository.domain.entity.SensorStringMessage

interface SensorStringMessageRepository {
    fun save(entity: SensorStringMessage)
}