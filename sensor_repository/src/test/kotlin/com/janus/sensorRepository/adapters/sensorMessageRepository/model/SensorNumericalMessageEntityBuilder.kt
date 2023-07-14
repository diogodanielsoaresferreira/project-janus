package com.janus.sensorRepository.adapters.sensorMessageRepository.model

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class SensorNumericalMessageEntityBuilder(
    val id: Long = 1,
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val value: Double = 1.5
) {
    fun build() = SensorNumericalMessageEntity(id, sensorId, timestamp, value)
}
