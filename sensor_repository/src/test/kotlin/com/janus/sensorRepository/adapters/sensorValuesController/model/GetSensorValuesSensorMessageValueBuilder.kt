package com.janus.sensorRepository.adapters.sensorValuesController.model

import com.janus.sensorRepository.domain.entity.ValueType
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class GetSensorValuesSensorMessageValueBuilder(
    val sensorId: String = "sensorId",
    val timestamp: OffsetDateTime = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
    val value: String = "value",
    val valueType: ValueType = ValueType.STRING
) {
    fun build(): GetSensorValuesSensorMessageValue =
        GetSensorValuesSensorMessageValue(sensorId = sensorId, timestamp = timestamp, value = value, valueType = valueType)
}