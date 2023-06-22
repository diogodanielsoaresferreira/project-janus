package com.janus.sensorRepository.adapters.sensorMessageProcessor.model

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class SensorMessageEventBuilder(
    val event: String = "event",
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val valueType: String = "string",
    val value: String = "value"
) {
    fun build() = SensorMessageEvent(event, sensorId, timestamp, valueType, value)
}
