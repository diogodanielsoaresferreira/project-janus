package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorMessage
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.ValueType
import java.time.LocalDateTime

interface GetSensorValuesUseCase {
    fun execute(sensorId: String, from: LocalDateTime, to: LocalDateTime, sort: SortOrder): List<SensorMessage>
    fun execute(sensorId: String, from: LocalDateTime, to: LocalDateTime, sort: SortOrder, valueType: ValueType): List<SensorMessage>
}