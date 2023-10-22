package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.toSensorMessage
import com.janus.sensorRepository.domain.port.SensorMessageRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.LocalDateTime

class GetSensorValuesUseCaseImplTest {

    private val repository: SensorMessageRepository = mock()
    private val victim = GetSensorValuesUseCaseImpl(repository)

    @Test
    fun `Get sensor values for both valueTypes sort by ascending`() {
        val sensorId = "sensorId"
        val now = LocalDateTime.now()
        val from = now.minusMonths(1).toSystemOffsetDateTime()
        val to = now.toSystemOffsetDateTime()

        val stringMessage = SensorStringMessageBuilder().build()
        val stringMessage2 = SensorStringMessageBuilder(timestamp = now.minusDays(1).toSystemOffsetDateTime()).build()
        val stringMessage3 = SensorStringMessageBuilder(timestamp = now.minusHours(1).toSystemOffsetDateTime()).build()

        val numericalMessage = SensorNumericalMessageBuilder(timestamp = now.minusDays(2).toSystemOffsetDateTime()).build()
        val numericalMessage2 =
            SensorNumericalMessageBuilder(timestamp = now.minusDays(1).minusSeconds(1).toSystemOffsetDateTime()).build()
        val numericalMessage3 = SensorNumericalMessageBuilder(
            timestamp = now.minusHours(1).minusSeconds(1).toSystemOffsetDateTime()
        ).build()

        val sortedMessages = listOf(
            stringMessage.toSensorMessage(),
            numericalMessage.toSensorMessage(),
            numericalMessage2.toSensorMessage(),
            stringMessage2.toSensorMessage(),
            numericalMessage3.toSensorMessage(),
            stringMessage3.toSensorMessage()
        )

        val sensorStringMessages = listOf(
            stringMessage,
            stringMessage2,
            stringMessage3
        )

        val sensorNumericalMessages = listOf(
            numericalMessage,
            numericalMessage2,
            numericalMessage3
        )

        Mockito.`when`(repository.getSensorStringMessages(sensorId, from, to, SortOrder.ASCENDING)).thenReturn(sensorStringMessages)
        Mockito.`when`(repository.getSensorNumericalMessages(sensorId, from, to, SortOrder.ASCENDING)).thenReturn(sensorNumericalMessages)

        val values = victim.execute(sensorId, from.toLocalDateTime(), to.toLocalDateTime(), SortOrder.ASCENDING)

        assertEquals(6, values.size)
        assertEquals(sortedMessages, values)
    }

    @Test
    fun `Get sensor values for both valueTypes sort by Descending`() {
        val sensorId = "sensorId"
        val now = LocalDateTime.now()
        val from = now.minusMonths(1).toSystemOffsetDateTime()
        val to = now.toSystemOffsetDateTime()

        val stringMessage = SensorStringMessageBuilder().build()
        val stringMessage2 = SensorStringMessageBuilder(timestamp = now.minusDays(1).toSystemOffsetDateTime()).build()
        val stringMessage3 = SensorStringMessageBuilder(timestamp = now.minusHours(1).toSystemOffsetDateTime()).build()

        val numericalMessage = SensorNumericalMessageBuilder(timestamp = now.minusDays(2).toSystemOffsetDateTime()).build()
        val numericalMessage2 =
            SensorNumericalMessageBuilder(timestamp = now.minusDays(1).minusSeconds(1).toSystemOffsetDateTime()).build()
        val numericalMessage3 = SensorNumericalMessageBuilder(
            timestamp = now.minusHours(1).minusSeconds(1).toSystemOffsetDateTime()
        ).build()

        val sortedMessages = listOf(
            stringMessage3.toSensorMessage(),
            numericalMessage3.toSensorMessage(),
            stringMessage2.toSensorMessage(),
            numericalMessage2.toSensorMessage(),
            numericalMessage.toSensorMessage(),
            stringMessage.toSensorMessage()
        )

        val sensorStringMessages = listOf(
            stringMessage,
            stringMessage2,
            stringMessage3
        )

        val sensorNumericalMessages = listOf(
            numericalMessage,
            numericalMessage2,
            numericalMessage3
        )

        Mockito.`when`(repository.getSensorStringMessages(sensorId, from, to, SortOrder.DESCENDING)).thenReturn(sensorStringMessages)
        Mockito.`when`(repository.getSensorNumericalMessages(sensorId, from, to, SortOrder.DESCENDING)).thenReturn(sensorNumericalMessages)

        val values = victim.execute(sensorId, from.toLocalDateTime(), to.toLocalDateTime(), SortOrder.DESCENDING)

        assertEquals(6, values.size)
        assertEquals(sortedMessages, values)
    }

    @Test
    fun `Get sensor values for both valueTypes returns empty for no values`() {
        val sensorId = "sensorId"
        val now = LocalDateTime.now()
        val from = now.minusMonths(1).toSystemOffsetDateTime()
        val to = now.toSystemOffsetDateTime()

        val values = victim.execute(sensorId, from.toLocalDateTime(), to.toLocalDateTime(), SortOrder.ASCENDING)

        assertEquals(0, values.size)
    }

    @Test
    fun `Get sensor values for numerical valueType`() {
        val sensorId = "sensorId"
        val now = LocalDateTime.now()
        val from = now.minusMonths(1).toSystemOffsetDateTime()
        val to = now.toSystemOffsetDateTime()

        val numericalMessage = SensorNumericalMessageBuilder(timestamp = now.minusDays(2).toSystemOffsetDateTime()).build()
        val numericalMessage2 =
            SensorNumericalMessageBuilder(timestamp = now.minusDays(1).minusSeconds(1).toSystemOffsetDateTime()).build()
        val numericalMessage3 = SensorNumericalMessageBuilder(
            timestamp = now.minusHours(1).minusSeconds(1).toSystemOffsetDateTime()
        ).build()

        val sortedMessages = listOf(
            numericalMessage.toSensorMessage(),
            numericalMessage2.toSensorMessage(),
            numericalMessage3.toSensorMessage(),
        )

        val sensorNumericalMessages = listOf(
            numericalMessage,
            numericalMessage2,
            numericalMessage3
        )

        Mockito.`when`(repository.getSensorNumericalMessages(sensorId, from, to, SortOrder.ASCENDING)).thenReturn(sensorNumericalMessages)

        val values = victim.execute(sensorId, from.toLocalDateTime(), to.toLocalDateTime(), SortOrder.ASCENDING)

        assertEquals(3, values.size)
        assertEquals(sortedMessages, values)
    }

    @Test
    fun `Get sensor values for string valueType`() {
        val sensorId = "sensorId"
        val now = LocalDateTime.now()
        val from = now.minusMonths(1).toSystemOffsetDateTime()
        val to = now.toSystemOffsetDateTime()

        val stringMessage = SensorStringMessageBuilder().build()
        val stringMessage2 = SensorStringMessageBuilder(timestamp = now.minusDays(1).toSystemOffsetDateTime()).build()
        val stringMessage3 = SensorStringMessageBuilder(timestamp = now.minusHours(1).toSystemOffsetDateTime()).build()

        val sortedMessages = listOf(
            stringMessage.toSensorMessage(),
            stringMessage2.toSensorMessage(),
            stringMessage3.toSensorMessage()
        )

        val sensorStringMessages = listOf(
            stringMessage,
            stringMessage2,
            stringMessage3
        )

        Mockito.`when`(repository.getSensorStringMessages(sensorId, from, to, SortOrder.ASCENDING)).thenReturn(sensorStringMessages)

        val values = victim.execute(sensorId, from.toLocalDateTime(), to.toLocalDateTime(), SortOrder.ASCENDING)

        assertEquals(3, values.size)
        assertEquals(sortedMessages, values)
    }

}