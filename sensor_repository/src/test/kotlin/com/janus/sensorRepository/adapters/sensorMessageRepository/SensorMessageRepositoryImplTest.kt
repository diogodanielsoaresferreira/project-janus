package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntityBuilder
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntityBuilder
import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import com.janus.sensorRepository.domain.entity.SortOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import java.time.OffsetDateTime
import java.time.ZoneOffset


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class SensorMessageRepositoryImplTest(
    @Autowired private val sensorRepository: SensorMessageRepositoryImpl,
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    private val sensorRepoTestUtils = SensorMessageRepositoryTestingUtils(jdbcTemplate)
    private val timestampReference = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)


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

    @Test
    fun `get 5 string messages sorted by ascending order`() {
        saveSensorStringMessages(5)

        val result = sensorRepository.getSensorStringMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusYears(1),
            SortOrder.ASCENDING
        )

        assertEquals(5, result.size)
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference, value="1").build(), result[0])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(1), value="2").build(), result[1])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(2), value="3").build(), result[2])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(3), value="4").build(), result[3])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(4), value="5").build(), result[4])
    }

    @Test
    fun `get 5 string messages sorted by descending order`() {
        saveSensorStringMessages(5)

        val result = sensorRepository.getSensorStringMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusYears(1),
            SortOrder.DESCENDING
        )

        assertEquals(5, result.size)
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(4), value="5").build(), result[0])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(3), value="4").build(), result[1])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(2), value="3").build(), result[2])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(1), value="2").build(), result[3])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference, value="1").build(), result[4])
    }

    @Test
    fun `get 5 string messages sorted by ascending order and 3 are left out because timestamp is later than to`() {
        saveSensorStringMessages(5)

        val result = sensorRepository.getSensorStringMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusDays(1),
            SortOrder.ASCENDING
        )

        assertEquals(2, result.size)
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference, value="1").build(), result[0])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(1), value="2").build(), result[1])
    }

    @Test
    fun `get 5 string messages sorted by ascending order and 3 are left out because timestamp is earlier than from`() {
        saveSensorStringMessages(5)

        val result = sensorRepository.getSensorStringMessages(
            "sensorId",
            timestampReference.plusDays(3),
            timestampReference.plusYears(1),
            SortOrder.ASCENDING
        )

        assertEquals(2, result.size)
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(3), value="4").build(), result[0])
        assertEquals(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(4), value="5").build(), result[1])
    }

    @Test
    fun `get 5 string messages sorted by ascending order and 5 are left out because some have timestamp earlier than from and other have timestamp later than to`() {
        saveSensorStringMessages(5)

        val result = sensorRepository.getSensorStringMessages(
            "sensorId",
            timestampReference.plusDays(1).plusHours(1),
            timestampReference.plusDays(1).plusHours(23),
            SortOrder.ASCENDING
        )

        assertEquals(0, result.size)
    }

    @Test
    fun `get empty list of string messages due to no messages in repository`() {
        val result = sensorRepository.getSensorStringMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusDays(5).plusHours(2),
            SortOrder.ASCENDING
        )

        assertEquals(0, result.size)
    }

    @Test
    fun `get empty list of string messages due to sensor does not exist in repository`() {
        saveSensorStringMessages(5)

        val result = sensorRepository.getSensorStringMessages(
            "sensorId2",
            timestampReference,
            timestampReference.plusYears(1),
            SortOrder.ASCENDING
        )

        assertEquals(0, result.size)
    }

    @Test
    fun `get 5 numerical messages sorted by ascending order`() {
        saveSensorNumericalMessages(5)

        val result = sensorRepository.getSensorNumericalMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusYears(1),
            SortOrder.ASCENDING
        )

        assertEquals(5, result.size)
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference, value=1.0).build(), result[0])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(1), value=2.0).build(), result[1])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(2), value=3.0).build(), result[2])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(3), value=4.0).build(), result[3])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(4), value=5.0).build(), result[4])
    }

    @Test
    fun `get 5 numerical messages sorted by descending order`() {
        saveSensorNumericalMessages(5)

        val result = sensorRepository.getSensorNumericalMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusYears(1),
            SortOrder.DESCENDING
        )

        assertEquals(5, result.size)
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(4), value=5.0).build(), result[0])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(3), value=4.0).build(), result[1])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(2), value=3.0).build(), result[2])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(1), value=2.0).build(), result[3])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference, value=1.0).build(), result[4])
    }

    @Test
    fun `get 5 numerical messages sorted by ascending order and 3 are left out because timestamp is later than to`() {
        saveSensorNumericalMessages(5)

        val result = sensorRepository.getSensorNumericalMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusDays(1),
            SortOrder.ASCENDING
        )

        assertEquals(2, result.size)
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference, value=1.0).build(), result[0])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(1), value=2.0).build(), result[1])
    }

    @Test
    fun `get 5 numerical messages sorted by ascending order and 3 are left out because timestamp is earlier than from`() {
        saveSensorNumericalMessages(5)

        val result = sensorRepository.getSensorNumericalMessages(
            "sensorId",
            timestampReference.plusDays(3),
            timestampReference.plusYears(1),
            SortOrder.ASCENDING
        )

        assertEquals(2, result.size)
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(3), value=4.0).build(), result[0])
        assertEquals(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(4), value=5.0).build(), result[1])
    }

    @Test
    fun `get 5 numerical messages sorted by ascending order and 5 are left out because some have timestamp earlier than from and other have timestamp later than to`() {
        saveSensorNumericalMessages(5)

        val result = sensorRepository.getSensorNumericalMessages(
            "sensorId",
            timestampReference.plusDays(1).plusHours(1),
            timestampReference.plusDays(1).plusHours(23),
            SortOrder.ASCENDING
        )

        assertEquals(0, result.size)
    }

    @Test
    fun `get empty list of numerical messages due to no messages in repository`() {
        val result = sensorRepository.getSensorNumericalMessages(
            "sensorId",
            timestampReference,
            timestampReference.plusDays(5).plusHours(2),
            SortOrder.ASCENDING
        )

        assertEquals(0, result.size)
    }

    @Test
    fun `get empty list of numerical messages due to sensor does not exist in repository`() {
        saveSensorNumericalMessages(5)

        val result = sensorRepository.getSensorNumericalMessages(
            "sensorId2",
            timestampReference,
            timestampReference.plusYears(1),
            SortOrder.ASCENDING
        )

        assertEquals(0, result.size)
    }

    private fun saveSensorStringMessages(numberOfMessages: Int) {
        for(i in 0 until numberOfMessages) {
            sensorRepository.save(SensorStringMessageBuilder(timestamp=timestampReference.plusDays(i.toLong()), value=(i+1).toString()).build())
        }
    }

    private fun saveSensorNumericalMessages(numberOfMessages: Int) {
        for(i in 0 until numberOfMessages) {
            sensorRepository.save(SensorNumericalMessageBuilder(timestamp=timestampReference.plusDays(i.toLong()), value=
            (i+1).toDouble()).build())
        }
    }

}
