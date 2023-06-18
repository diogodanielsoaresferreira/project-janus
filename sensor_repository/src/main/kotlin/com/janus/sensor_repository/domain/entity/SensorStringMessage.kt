package com.janus.sensor_repository.domain.entity

import java.time.OffsetDateTime

data class SensorStringMessage(
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: String
)
