package com.janus.sensorRepository.adapters.sensorValuesController.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.janus.sensorRepository.domain.entity.ValueType
import java.time.OffsetDateTime

data class GetSensorValuesSensorMessageValue(
    @JsonProperty("sensor_id")
    val sensorId: String,
    val timestamp: OffsetDateTime,
    val value: String,
    @JsonProperty("value_type")
    val valueType: ValueType
)
