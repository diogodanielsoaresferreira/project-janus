package com.janus.sensorRepository.adapters.sensorMessageProcessor

import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.SensorMessageEventBuilder
import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.toSensorNumericalMessage
import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.toSensorStringMessage
import com.janus.sensorRepository.domain.port.SaveSensorMessageUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.Mockito.verifyNoInteractions
import java.lang.NumberFormatException

internal class SensorMessageProcessorTest {

    private val useCaseMessage: SaveSensorMessageUseCase = mock()
    private val victim = SensorMessageProcessor(useCaseMessage)

    @Test
    fun `when receive SensorMessageEvent with valueType string calls SaveSensorMessage Use case once with correct arguments`() {
        val event = SensorMessageEventBuilder().build()
        victim.process(event)
        verify(useCaseMessage, times(1)).execute(event.toSensorStringMessage())
    }

    @Test
    fun `when receive SensorMessageEvent with valueType numerical calls SaveSensorMessage Use case once with correct arguments`() {
        val event = SensorMessageEventBuilder(valueType = "numerical", value = "1.5").build()
        victim.process(event)
        verify(useCaseMessage, times(1)).execute(event.toSensorNumericalMessage())
    }

    @Test
    fun `when receive SensorMessageEvent with valueType numerical with wrong value throws exception`() {
        val event = SensorMessageEventBuilder(valueType = "numerical", value = "error").build()
        assertThrows<NumberFormatException> { victim.process(event) }
        verifyNoInteractions(useCaseMessage)
    }

    @Test
    fun `when receive SensorMessageEvent with unknown valueType throws SensorMessageEventProcessingError exception`() {
        val event = SensorMessageEventBuilder(valueType="unknown").build()
        assertThrows<SensorMessageEventProcessingError> { victim.process(event) }
        verifyNoInteractions(useCaseMessage)
    }

}