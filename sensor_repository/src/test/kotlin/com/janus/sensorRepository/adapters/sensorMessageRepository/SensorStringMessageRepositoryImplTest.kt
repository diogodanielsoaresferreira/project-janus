package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntityBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class SensorStringMessageRepositoryImplTest(
    @Autowired private val sensorStringRepository: SensorStringMessageRepositoryImpl,
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    private val sensorStringRepoTestUtils: SensorStringMessageRepositoryTestingUtils =
        SensorStringMessageRepositoryTestingUtils(jdbcTemplate)

    @Test
    fun `save SensorStringMessage successfully`() {
        sensorStringRepository.save(SensorStringMessageBuilder().build())
        assertEquals(1, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
        assertEquals(SensorStringMessageEntityBuilder().build(), sensorStringRepoTestUtils.getElementInRepository())
    }

    @Test
    fun `save duplicated SensorStringMessage successfully`() {
        sensorStringRepository.save(SensorStringMessageBuilder().build())
        sensorStringRepository.save(SensorStringMessageBuilder().build())
        assertEquals(2, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
        val elementsInRepo = sensorStringRepoTestUtils.getElementsInRepository()
        assertEquals(SensorStringMessageEntityBuilder().build(), elementsInRepo[0])
        assertEquals(SensorStringMessageEntityBuilder(id=2).build(), elementsInRepo[1])
    }

}