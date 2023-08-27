package com.janus.sensorRepository.adapters.sensorMessageRepository.model

import com.janus.sensorRepository.domain.entity.SensorStringMessage
import java.time.OffsetDateTime

data class SensorStringMessageEntity(
    val id: Long,
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: String
)

fun SensorStringMessageEntity.toSensorStringMessage(): SensorStringMessage = SensorStringMessage(
    sensorId = sensorId,
    timestamp = timestamp,
    value = value
)
