package com.janus.sensorRepository.adapters.sensorMessageRepository.model

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import java.time.OffsetDateTime

data class SensorNumericalMessageEntity(
    val id: Long,
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: Double
)

fun SensorNumericalMessageEntity.toSensorNumericalMessage(): SensorNumericalMessage = SensorNumericalMessage(
    sensorId = sensorId,
    timestamp = timestamp,
    value = value
)
