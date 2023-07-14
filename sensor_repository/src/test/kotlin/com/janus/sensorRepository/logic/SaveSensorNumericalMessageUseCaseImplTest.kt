package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.port.SensorNumericalMessageRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class SaveSensorNumericalMessageUseCaseImplTest {

    private val repository: SensorNumericalMessageRepository = mock()
    private val victim = SaveSensorNumericalMessageUseCaseImpl(repository)

    @Test
    fun `Save Sensor Numerical Message Use Case calls save once on Sensor Numerical Message Repository`() {
        val sensorMessage = SensorNumericalMessageBuilder().build()
        victim.execute(sensorMessage)
        verify(repository, times(1)).save(sensorMessage)
    }

}