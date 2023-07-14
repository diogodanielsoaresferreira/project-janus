package com.janus.sensorRepository.adapters.sensorMessageRepository.model

import java.time.OffsetDateTime

data class SensorNumericalMessageEntity(
    val id: Long,
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: Double
)
