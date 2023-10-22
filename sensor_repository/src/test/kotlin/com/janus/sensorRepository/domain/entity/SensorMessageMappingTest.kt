package com.janus.sensorRepository.domain.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SensorMessageMappingTest {

    @Test
    fun `convert sensorStringMessage to SensorMessage` () {
        assertEquals(SensorMessageBuilder().build(), SensorStringMessageBuilder().build().toSensorMessage())
    }

    @Test
    fun `convert sensorNumericalMessage to SensorMessage` () {
        assertEquals(SensorMessageBuilder(value = "1.5", valueType = ValueType.NUMERICAL).build(), SensorNumericalMessageBuilder().build().toSensorMessage())
    }
}