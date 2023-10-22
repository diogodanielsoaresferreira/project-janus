package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorMessage
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.ValueType
import com.janus.sensorRepository.domain.entity.toSensorMessage
import com.janus.sensorRepository.domain.port.GetSensorValuesUseCase
import com.janus.sensorRepository.domain.port.SensorMessageRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

@Component
class GetSensorValuesUseCaseImpl(
    @Autowired private val repository: SensorMessageRepository
): GetSensorValuesUseCase {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(sensorId: String, from: LocalDateTime, to: LocalDateTime, sort: SortOrder): List<SensorMessage> {
        logger.debug(
            "operation=execute, message='getSensorValues for sensor {} from {} to {}, sorted by {} with all valueTypes'",
            sensorId,
            from,
            to,
            sort
        )
        val unsortedValues = ValueType.values().flatMap {
            getSensorValues(sensorId, from, to, sort, it)
        }

        return when(sort) {
            SortOrder.ASCENDING -> unsortedValues.sortedBy { it.timestamp }
            SortOrder.DESCENDING -> unsortedValues.sortedByDescending { it.timestamp }
        }
    }

    override fun execute(sensorId: String, from: LocalDateTime, to: LocalDateTime, sort: SortOrder, valueType: ValueType): List<SensorMessage> {
        logger.debug(
            "operation=execute, message='getSensorValues for sensor {} from {} to {}, sorted by {} with valueType {}'",
            sensorId,
            from,
            to,
            sort,
            valueType
        )
        return getSensorValues(sensorId, from, to, sort, valueType)
    }

    private fun getSensorValues(sensorId: String, from: LocalDateTime, to: LocalDateTime, sort: SortOrder, valueType: ValueType): List<SensorMessage> =
        when(valueType) {
            ValueType.STRING -> repository.getSensorStringMessages(
                sensorId,
                from.toSystemOffsetDateTime(),
                to.toSystemOffsetDateTime(),
                sort
            ).map { it.toSensorMessage() }
            ValueType.NUMERICAL -> repository.getSensorNumericalMessages(
                sensorId,
                from.toSystemOffsetDateTime(),
                to.toSystemOffsetDateTime(),
                sort
            ).map { it.toSensorMessage() }
        }
}

fun LocalDateTime.toSystemOffsetDateTime(): OffsetDateTime = this.atZone(ZoneId.systemDefault()).toOffsetDateTime()
