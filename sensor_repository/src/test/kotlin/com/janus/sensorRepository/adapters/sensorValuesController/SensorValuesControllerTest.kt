package com.janus.sensorRepository.adapters.sensorValuesController

import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesRequestParametersBuilder
import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesResponseBuilder
import com.janus.sensorRepository.domain.entity.SensorMessageBuilder
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.ValueType
import com.janus.sensorRepository.domain.port.GetSensorValuesUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

class SensorValuesControllerTest {

    private val useCase: GetSensorValuesUseCase = Mockito.mock()
    private val victim = SensorValuesController(useCase)

    @Test
    fun `given a request, return correct response` () {
        val sensorId = "sensorId"
        val from = LocalDateTime.of(2023, 1, 1, 0, 0, 0)
        val to = LocalDateTime.of(2023, 3, 1, 0, 0, 0)
        val sort = SortOrder.ASCENDING
        val valueType = ValueType.STRING
        val expectedResponse = GetSensorValuesResponseBuilder().build()

        Mockito.`when`(useCase.execute(sensorId, from, to, sort, valueType)).thenReturn(listOf(
            SensorMessageBuilder().build(),
            SensorMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                value = "value2"
            ).build(),
            SensorMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                value = "value3"
            ).build()
        ))
        val values = victim.getValues(
            sensorId,
            from,
            to,
            sort,
            valueType
        )
        assertEquals(expectedResponse, values)
        verify(useCase, times(1)).execute(sensorId, from, to, sort, valueType)
    }

    @Test
    fun `given a request without valueType, return values with all valueTypes from that sensor` () {
        val sensorId = "sensorId"
        val from = LocalDateTime.of(2023, 1, 1, 0, 0, 0)
        val to = LocalDateTime.of(2023, 3, 1, 0, 0, 0)
        val sort = SortOrder.ASCENDING
        val valueType = null
        val expectedResponse = GetSensorValuesResponseBuilder(request = GetSensorValuesRequestParametersBuilder(valueType = valueType).build()).build()

        Mockito.`when`(useCase.execute(sensorId, from, to, sort)).thenReturn(listOf(
            SensorMessageBuilder().build(),
            SensorMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                value = "value2"
            ).build(),
            SensorMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                value = "value3"
            ).build()
        ))
        val values = victim.getValues(
            sensorId,
            from,
            to,
            sort,
            valueType
        )
        assertEquals(expectedResponse, values)
        verify(useCase, times(1)).execute(sensorId, from, to, sort)
    }

    @Test
    fun `given a request where the to parameter is before the from parameter, throw Bad Request` () {

        val sensorId = "sensorId"
        val from = LocalDateTime.of(2023, 1, 1, 0, 0, 0)
        val to = LocalDateTime.of(2022, 3, 1, 0, 0, 0)
        val sort = SortOrder.ASCENDING
        val valueType = ValueType.STRING

        assertThrows<ResponseStatusException> { victim.getValues(
            sensorId,
            from,
            to,
            sort,
            valueType
        )}
    }

    @Test
    fun `given a request where the interval between from and to requests is higher than 90 days, throw Bad Request` () {
        val sensorId = "sensorId"
        val from = LocalDateTime.of(2023, 1, 1, 0, 0, 0)
        val to = LocalDateTime.of(2024, 3, 1, 0, 0, 0)
        val sort = SortOrder.ASCENDING
        val valueType = ValueType.STRING

        assertThrows<ResponseStatusException> { victim.getValues(
            sensorId,
            from,
            to,
            sort,
            valueType
        )}
    }

}