package com.janus.sensorRepository.adapters.sensorValuesController.model

data class GetSensorValuesResponse(
    val request: GetSensorValuesRequestParameters,
    val values: List<GetSensorValuesSensorMessageValue>
)
