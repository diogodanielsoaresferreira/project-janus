package com.janus.sensor_repository.adapters.sensor_messages

import com.janus.sensor_repository.domain.entity.SensorMessageEventBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class SensorMessagesProducerTest {

    private val victim = SensorMessagesProcessor()

    @Test
    fun `process correctly SensorMessageEvent`() {
        assertDoesNotThrow { victim.process(SensorMessageEventBuilder().build()) }
    }

}