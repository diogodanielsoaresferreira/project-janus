package com.janus.sensorRepository.adapters.sensorValuesController.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.ValueType
import java.time.LocalDateTime

data class GetSensorValuesRequestParameters(
    @JsonProperty("sensor_id")
    val sensorId: String,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val sort: SortOrder,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value_type")
    val valueType: ValueType?
)
