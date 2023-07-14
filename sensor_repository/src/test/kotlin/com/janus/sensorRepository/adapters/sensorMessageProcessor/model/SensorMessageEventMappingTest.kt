package com.janus.sensorRepository.adapters.sensorMessageProcessor.model

import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SensorMessageEventMappingTest {

    @Test
    fun `convert sensorMessageEvent to SensorStringMessage successfully`() {
        assertEquals(SensorStringMessageBuilder().build(), SensorMessageEventBuilder().build().toSensorStringMessage())
    }

    @Test
    fun `convert sensorMessageEvent to SensorNumericalMessage successfully`() {
        assertEquals(SensorNumericalMessageBuilder().build(), SensorMessageEventBuilder(valueType = "numerical", value = "1.5").build().toSensorNumericalMessage())
    }

}