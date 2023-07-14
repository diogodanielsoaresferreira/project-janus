package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntityBuilder
import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class SensorNumericalMessageRepositoryImplTest(
    @Autowired private val sensorNumericalRepository: SensorNumericalMessageRepositoryImpl,
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    private val sensorNumericalRepoTestUtils: SensorNumericalMessageRepositoryTestingUtils =
        SensorNumericalMessageRepositoryTestingUtils(jdbcTemplate)

    @Test
    fun `save SensorNumericalMessage successfully`() {
        sensorNumericalRepository.save(SensorNumericalMessageBuilder().build())
        assertEquals(1, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
        assertEquals(SensorNumericalMessageEntityBuilder().build(), sensorNumericalRepoTestUtils.getElementInRepository())
    }

    @Test
    fun `save duplicated SensorNumericalMessage successfully`() {
        sensorNumericalRepository.save(SensorNumericalMessageBuilder().build())
        sensorNumericalRepository.save(SensorNumericalMessageBuilder().build())
        assertEquals(2, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
        val elementsInRepo = sensorNumericalRepoTestUtils.getElementsInRepository()
        assertEquals(SensorNumericalMessageEntityBuilder().build(), elementsInRepo[0])
        assertEquals(SensorNumericalMessageEntityBuilder(id=2).build(), elementsInRepo[1])
    }

}