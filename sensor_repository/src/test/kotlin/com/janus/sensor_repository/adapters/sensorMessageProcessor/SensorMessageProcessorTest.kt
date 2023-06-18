package com.janus.sensor_repository.adapters.sensorMessageProcessor

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class SensorMessageProcessorTest {

    private val victim = SensorMessageProcessor()

    @Test
    fun `process correctly SensorMessageEvent`() {
        assertDoesNotThrow { victim.process(SensorMessageEventBuilder().build()) }
    }

}