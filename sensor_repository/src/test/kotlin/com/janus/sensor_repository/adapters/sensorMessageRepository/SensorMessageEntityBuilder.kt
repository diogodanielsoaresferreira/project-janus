package com.janus.sensor_repository.adapters.sensorMessageRepository

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class SensorMessageEntityBuilder(
    val id: Long = 1,
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val value: String = "value"
) {
    fun build() = SensorMessageEntity(id, sensorId, timestamp, value)
}
