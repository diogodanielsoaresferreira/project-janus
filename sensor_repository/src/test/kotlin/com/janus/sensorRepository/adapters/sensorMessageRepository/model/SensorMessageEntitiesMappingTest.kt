package com.janus.sensorRepository.adapters.sensorMessageRepository.model

import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SensorMessageEntitiesMappingTest {

    @Test
    fun `convert sensorStringMessageEntity to SensorStringMessage` () {
        assertEquals(SensorStringMessageBuilder().build(), SensorStringMessageEntityBuilder().build().toSensorStringMessage())
    }

    @Test
    fun `convert sensorNumericalMessageEntity to SensorNumericalMessage` () {
        assertEquals(SensorNumericalMessageBuilder().build(), SensorNumericalMessageEntityBuilder().build().toSensorNumericalMessage())
    }

}
