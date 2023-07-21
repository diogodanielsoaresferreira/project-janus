package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntityBuilder
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntityBuilder
import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class SensorMessageRepositoryImplTest(
    @Autowired private val sensorRepository: SensorMessageRepositoryImpl,
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    private val sensorRepoTestUtils = SensorMessageRepositoryTestingUtils(jdbcTemplate)


    @Test
    fun `save SensorNumericalMessage successfully`() {
        sensorRepository.save(SensorNumericalMessageBuilder().build())
        assertEquals(1, sensorRepoTestUtils.getNumberOfNumericalMessages())
        assertEquals(SensorNumericalMessageEntityBuilder().build(), sensorRepoTestUtils.getNumericalMessages()[0])
    }

    @Test
    fun `save duplicated SensorNumericalMessage successfully`() {
        sensorRepository.save(SensorNumericalMessageBuilder().build())
        sensorRepository.save(SensorNumericalMessageBuilder().build())
        assertEquals(2, sensorRepoTestUtils.getNumberOfNumericalMessages())
        val elementsInRepo = sensorRepoTestUtils.getNumericalMessages()
        assertEquals(SensorNumericalMessageEntityBuilder().build(), elementsInRepo[0])
        assertEquals(SensorNumericalMessageEntityBuilder(id=2).build(), elementsInRepo[1])
    }

    @Test
    fun `save SensorStringMessage successfully`() {
        sensorRepository.save(SensorStringMessageBuilder().build())
        assertEquals(1, sensorRepoTestUtils.getNumberOfStringMessages())
        assertEquals(SensorStringMessageEntityBuilder().build(), sensorRepoTestUtils.getStringMessages()[0])
    }

    @Test
    fun `save duplicated SensorStringMessage successfully`() {
        sensorRepository.save(SensorStringMessageBuilder().build())
        sensorRepository.save(SensorStringMessageBuilder().build())
        assertEquals(2, sensorRepoTestUtils.getNumberOfStringMessages())
        val elementsInRepo = sensorRepoTestUtils.getStringMessages()
        assertEquals(SensorStringMessageEntityBuilder().build(), elementsInRepo[0])
        assertEquals(SensorStringMessageEntityBuilder(id=2).build(), elementsInRepo[1])
    }

}