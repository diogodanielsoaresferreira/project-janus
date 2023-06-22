package com.janus.sensorRepository.adapters.sensorMessageProcessor.model

import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SensorMessageEventMappingTest {

    @Test
    fun `convert sensorMessageEvent to SensorStringMessage successfully`() {
        assertEquals(SensorStringMessageBuilder().build(), SensorMessageEventBuilder().build().toSensorStringMessage())
    }

}