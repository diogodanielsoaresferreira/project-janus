package com.janus.sensorRepository.domain.entity

import java.time.OffsetDateTime

data class SensorNumericalMessage(
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: Double
)

fun SensorNumericalMessage.toSensorMessage() = SensorMessage(
    sensorId = sensorId,
    timestamp = timestamp,
    value = value.toString(),
    valueType = ValueType.NUMERICAL
)
