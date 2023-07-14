package com.janus.sensorRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntityBuilder
import com.janus.sensorRepository.adapters.sensorMessageProcessor.model.SensorMessageEventFileReader
import com.janus.sensorRepository.adapters.sensorMessageRepository.SensorNumericalMessageRepositoryTestingUtils
import com.janus.sensorRepository.adapters.sensorMessageRepository.SensorStringMessageRepositoryTestingUtils
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntityBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException
import org.springframework.amqp.rabbit.test.TestRabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import java.time.Duration


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SaveSensorMessageFlowTest(
	@Autowired private val template: TestRabbitTemplate,
	@Autowired private val jdbcTemplate: JdbcTemplate
) {

	private val sensorStringRepoTestUtils: SensorStringMessageRepositoryTestingUtils =
		SensorStringMessageRepositoryTestingUtils(jdbcTemplate)

	private val sensorNumericalRepoTestUtils: SensorNumericalMessageRepositoryTestingUtils =
		SensorNumericalMessageRepositoryTestingUtils(jdbcTemplate)

	@Test
	fun `event with valueType String is correctly stored in string repository`() {
		val event = SensorMessageEventFileReader.readFile("default.json")
		template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		await().atMost(Duration.ofSeconds(1)).untilAsserted {
			assertEquals(1, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
			assertEquals(SensorStringMessageEntityBuilder().build(), sensorStringRepoTestUtils.getElementInRepository())
			assertEquals(0, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
		}
	}

	@Test
	fun `two equal events with valueType String are correctly stored in string repository`() {
		val event = SensorMessageEventFileReader.readFile("default.json")
		template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		await().atMost(Duration.ofSeconds(1)).untilAsserted {
			assertEquals(2, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
			val elementsInRepository = sensorStringRepoTestUtils.getElementsInRepository()
			assertEquals(SensorStringMessageEntityBuilder().build(), elementsInRepository[0])
			assertEquals(SensorStringMessageEntityBuilder(id=2).build(), elementsInRepository[1])
			assertEquals(0, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
		}
	}

	@Test
	fun `event with numerical value is stored in numerical repository`() {
		val event = SensorMessageEventFileReader.readFile("numericalValue.json")
		template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		await().during(Duration.ofSeconds(1)).untilAsserted {
			assertEquals(1, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
			assertEquals(SensorNumericalMessageEntityBuilder().build(), sensorNumericalRepoTestUtils.getElementInRepository())
			assertEquals(0, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
		}
	}


	@Test
	fun `event with wrong numerical value is not stored in any repository`() {
		val event = SensorMessageEventFileReader.readFile("wrongNumericalValue.json")
		assertThrows<ListenerExecutionFailedException>{
			template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		}
		await().during(Duration.ofSeconds(1)).untilAsserted {
			assertEquals(0, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
			assertEquals(0, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
		}
	}

	@Test
	fun `event with unknown valueType is not stored in any repository`() {
		val event = SensorMessageEventFileReader.readFile("unknownValueType.json")
		assertThrows<ListenerExecutionFailedException>{
			template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		}
		await().during(Duration.ofSeconds(1)).untilAsserted {
			assertEquals(0, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
			assertEquals(0, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
		}
	}

	@Test
	fun `event with invalid timestamp is not stored in repository`() {
		val event = SensorMessageEventFileReader.readFile("malformedTimestamp.json")
		assertThrows<ListenerExecutionFailedException>{
			template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		}
		await().during(Duration.ofSeconds(1)).untilAsserted {
			assertEquals(0, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
			assertEquals(0, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
		}
	}

	@Test
	fun `event without value field is not stored in any repository`() {
		val event = SensorMessageEventFileReader.readFile("malformedTimestamp.json")
		assertThrows<ListenerExecutionFailedException>{
			template.send("sensor_consumer_queue_name", Message(event.toByteArray()))
		}
		await().during(Duration.ofSeconds(1)).untilAsserted {
			assertEquals(0, sensorStringRepoTestUtils.getNumberOfElementsInRepository())
			assertEquals(0, sensorNumericalRepoTestUtils.getNumberOfElementsInRepository())
		}
	}

}