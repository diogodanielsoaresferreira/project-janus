package com.janus.sensor_repository.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty
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
