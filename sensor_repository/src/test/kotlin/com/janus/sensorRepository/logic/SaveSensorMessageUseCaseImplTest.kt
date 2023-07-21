package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import com.janus.sensorRepository.domain.port.SensorMessageRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class SaveSensorMessageUseCaseImplTest {

    private val repository: SensorMessageRepository = mock()
    private val victim = SaveSensorMessageUseCaseImpl(repository)

    @Test
    fun `Save Sensor Numerical Message Use Case calls save once on Sensor Message Repository`() {
        val sensorMessage = SensorNumericalMessageBuilder().build()
        victim.execute(sensorMessage)
        verify(repository, times(1)).save(sensorMessage)
    }

    @Test
    fun `Save Sensor String Message Use Case calls save once on Sensor Message Repository`() {
        val sensorMessage = SensorStringMessageBuilder().build()
        victim.execute(sensorMessage)
        verify(repository, times(1)).save(sensorMessage)
    }

}