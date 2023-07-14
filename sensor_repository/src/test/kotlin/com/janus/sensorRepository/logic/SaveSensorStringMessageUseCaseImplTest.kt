package com.janus.sensorRepository.logic

import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import com.janus.sensorRepository.domain.port.SensorStringMessageRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class SaveSensorStringMessageUseCaseImplTest {

    private val repository: SensorStringMessageRepository = mock()
    private val victim = SaveSensorStringMessageUseCaseImpl(repository)

    @Test
    fun `Save Sensor String Message Use Case calls save once on Sensor String Message Repository`() {
        val sensorMessage = SensorStringMessageBuilder().build()
        victim.execute(sensorMessage)
        verify(repository, times(1)).save(sensorMessage)
    }

}