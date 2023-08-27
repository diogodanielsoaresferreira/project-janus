package com.janus.sensorRepository.domain.port

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage
import com.janus.sensorRepository.domain.entity.SortOrder
import java.time.OffsetDateTime

interface SensorMessageRepository {
    fun save(entity: SensorNumericalMessage)
    fun save(entity: SensorStringMessage)
    fun getSensorStringMessages(sensorName: String, from: OffsetDateTime, to: OffsetDateTime, sort: SortOrder): List<SensorStringMessage>
    fun getSensorNumericalMessages(sensorName: String, from: OffsetDateTime, to: OffsetDateTime, sort: SortOrder): List<SensorNumericalMessage>
}