package com.janus.sensorRepository.adapters.sensorValuesController.model

import java.time.OffsetDateTime
import java.time.ZoneOffset

data class GetSensorValuesResponseBuilder (
    val request: GetSensorValuesRequestParameters = GetSensorValuesRequestParametersBuilder().build(),
    val valueList: List<GetSensorValuesSensorMessageValue> = listOf(
        GetSensorValuesSensorMessageValueBuilder().build(),
        GetSensorValuesSensorMessageValueBuilder(timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC), value = "value2").build(),
        GetSensorValuesSensorMessageValueBuilder(timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC), value = "value3").build()

    )
) {
    fun build(): GetSensorValuesResponse =
        GetSensorValuesResponse(request, valueList)
}