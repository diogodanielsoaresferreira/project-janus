package com.janus.sensorRepository.adapters.sensorMessageProcessor

import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.SensorMessageEventBuilder
import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.toSensorStringMessage
import com.janus.sensorRepository.domain.port.SaveSensorStringMessageUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

internal class SensorMessageProcessorTest {

    private val useCase: SaveSensorStringMessageUseCase = mock()
    private val victim = SensorMessageProcessor(useCase)

    @Test
    fun `when receive SensorMessageEvent with valueType string calls SaveSensorStringMessage Use case once with correct arguments`() {
        val event = SensorMessageEventBuilder().build()
        victim.process(event)
        verify(useCase, times(1)).execute(event.toSensorStringMessage())
    }

    @Test
    fun `when receive SensorMessageEvent with unknown valueType throws SensorMessageEventProcessingError exception`() {
        val event = SensorMessageEventBuilder(valueType="unknown").build()
        assertThrows<SensorMessageEventProcessingError> { victim.process(event) }
        verifyNoInteractions(useCase)
    }

}