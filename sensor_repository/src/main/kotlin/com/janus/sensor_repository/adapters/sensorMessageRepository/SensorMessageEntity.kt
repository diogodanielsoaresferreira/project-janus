package com.janus.sensor_repository.adapters.sensorMessageRepository

import java.time.OffsetDateTime

data class SensorMessageEntity(
    val id: Long,
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: String
)
