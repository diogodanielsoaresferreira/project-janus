package com.janus.sensor_repository.adapters.sensorMessageProcessor

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class SensorMessageEventBuilder(
    val event: String = "event",
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val valueType: String = "valueType",
    val value: String = "value"
) {
    fun build() = SensorMessageEvent(event, sensorId, timestamp, valueType, value)
}
