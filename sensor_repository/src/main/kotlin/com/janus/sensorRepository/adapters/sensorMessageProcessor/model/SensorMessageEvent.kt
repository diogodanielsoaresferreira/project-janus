package com.janus.sensorRepository.adapters.sensorMessageProcessor.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage
import java.time.OffsetDateTime

data class SensorMessageEvent(
    @JsonProperty("event")
    val event: String,
    @JsonProperty("sensor_id")
    val sensorId: String,
    @JsonProperty("timestamp")
    val timestamp: OffsetDateTime,
    @JsonProperty("value_type")
    val valueType: String,
    @JsonProperty("value")
    val value: String
)

fun SensorMessageEvent.toSensorStringMessage(): SensorStringMessage = SensorStringMessage(
    sensorId=sensorId,
    timestamp=timestamp,
    value=value
)

fun SensorMessageEvent.toSensorNumericalMessage(): SensorNumericalMessage = SensorNumericalMessage(
    sensorId=sensorId,
    timestamp=timestamp,
    value=value.toDouble()
)
