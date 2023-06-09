package com.janus.sensor_repository.domain.entity

import java.time.OffsetDateTime

data class SensorMessageEventBuilder(
    val event: String = "event",
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.MIN,
    val valueType: String = "valueType",
    val value: String = "value"
) {
    fun build() = SensorMessageEvent(event, sensorId, timestamp, valueType, value)
}
