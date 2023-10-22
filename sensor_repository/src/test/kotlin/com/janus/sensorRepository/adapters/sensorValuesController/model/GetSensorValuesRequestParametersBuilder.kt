package com.janus.sensorRepository.adapters.sensorValuesController.model

import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.ValueType
import java.time.LocalDateTime

data class GetSensorValuesRequestParametersBuilder(
    val sensorId: String = "sensorId",
    val from: LocalDateTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
    val to: LocalDateTime = LocalDateTime.of(2023, 3, 1, 0, 0, 0),
    val sort: SortOrder = SortOrder.ASCENDING,
    val valueType: ValueType? = ValueType.STRING
) {
    fun build(): GetSensorValuesRequestParameters =
        GetSensorValuesRequestParameters(
            sensorId,
            from,
            to,
            sort,
            valueType
        )
}
