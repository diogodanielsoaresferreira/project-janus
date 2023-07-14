package com.janus.sensorRepository.adapters.sensorMessageRepository.model

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class SensorStringMessageEntityBuilder(
    val id: Long = 1,
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val value: String = "value"
) {
    fun build() = SensorStringMessageEntity(id, sensorId, timestamp, value)
}
