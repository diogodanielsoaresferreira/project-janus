package com.janus.sensorRepository.domain.entity

import java.time.OffsetDateTime

data class SensorMessage(
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: String,
    val valueType: ValueType
)
