package com.janus.sensorRepository.domain.entity

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class SensorMessageBuilder (
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val value: String = "value",
    val valueType: ValueType = ValueType.STRING
){
    fun build() = SensorMessage(sensorId, timestamp, value, valueType)
}