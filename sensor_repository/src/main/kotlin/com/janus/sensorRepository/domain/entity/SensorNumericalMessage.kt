package com.janus.sensorRepository.domain.entity

import java.time.OffsetDateTime

data class SensorNumericalMessage(
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: Double
)
