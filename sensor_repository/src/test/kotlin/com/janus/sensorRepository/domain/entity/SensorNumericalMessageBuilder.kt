package com.janus.sensorRepository.domain.entity

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class SensorNumericalMessageBuilder(
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val value: Double = 1.5
){
    fun build() = SensorNumericalMessage(sensorId, timestamp, value)
}
