package com.janus.sensorRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.SensorMessageRepositoryTestingUtils
import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesRequestParametersBuilder
import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesResponse
import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesResponseBuilder
import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesSensorMessageValueBuilder
import com.janus.sensorRepository.domain.entity.SensorNumericalMessageBuilder
import com.janus.sensorRepository.domain.entity.SensorStringMessageBuilder
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.ValueType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class GetSensorValuesFlowTest(
    @Autowired private val jdbcTemplate: JdbcTemplate,
    @Autowired private val restTemplate: TestRestTemplate,
    @LocalServerPort private val port: Int,
) {

    private val repoUtils = SensorMessageRepositoryTestingUtils(jdbcTemplate)

    @BeforeAll
    fun setupSensorValues() {
        val stringMessagesInRepo = listOf(
            SensorStringMessageBuilder().build(),
            SensorStringMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                value = "value2"
            ).build(),
            SensorStringMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                value = "value3"
            ).build(),
            SensorStringMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 3, 1, 0, 1, 0, 0, ZoneOffset.UTC),
                value = "value4"
            ).build(),
            SensorStringMessageBuilder(
                timestamp = OffsetDateTime.of(2022, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC),
                value = "value5"
            ).build()
        )

        val numericalMessagesInRepo = listOf(
            SensorNumericalMessageBuilder().build(),
            SensorNumericalMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                value = 2.0
            ).build(),
            SensorNumericalMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                value = 3.0
            ).build(),
            SensorNumericalMessageBuilder(
                timestamp = OffsetDateTime.of(2023, 3, 1, 0, 1, 0, 0, ZoneOffset.UTC),
                value = 4.0
            ).build(),
            SensorNumericalMessageBuilder(
                timestamp = OffsetDateTime.of(2022, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC),
                value = 5.0
            ).build()
        )

        for(message in stringMessagesInRepo) {
            repoUtils.insertSensorStringMessage(message)
        }

        for(message in numericalMessagesInRepo) {
            repoUtils.insertSensorNumericalMessage(message)
        }
    }

    @Test
    fun `request numerical values for two months`() {
        val resourceUrl = generateResourceUrl(
            from = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            to = LocalDateTime.of(2023, 3, 1, 0, 0, 0),
            valueType = ValueType.NUMERICAL
        )
        val response = restTemplate.getForObject(resourceUrl, GetSensorValuesResponse::class.java)
        assertEquals(GetSensorValuesResponseBuilder(
            request = GetSensorValuesRequestParametersBuilder(
                valueType = ValueType.NUMERICAL
            ).build(),
            valueList = listOf(
                GetSensorValuesSensorMessageValueBuilder(
                    value = "1.5",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "2.0",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "3.0",
                    valueType = ValueType.NUMERICAL
                ).build()
            )
        ).build(), response)
    }

    @Test
    fun `request string values for two months`() {
        val resourceUrl = generateResourceUrl(
            from = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            to = LocalDateTime.of(2023, 3, 1, 0, 0, 0),
            valueType = ValueType.STRING
        )
        val response = restTemplate.getForObject(resourceUrl, GetSensorValuesResponse::class.java)
        assertEquals(GetSensorValuesResponseBuilder().build(), response)
    }

    @Test
    fun `request numerical and string values for two months`() {
        val resourceUrl = generateResourceUrl(
            from = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            to = LocalDateTime.of(2023, 3, 1, 0, 0, 0)
        )
        val response = restTemplate.getForObject(resourceUrl, GetSensorValuesResponse::class.java)
        assertEquals(GetSensorValuesResponseBuilder(
            request = GetSensorValuesRequestParametersBuilder(
                valueType = null
            ).build(),
            valueList = listOf(
                GetSensorValuesSensorMessageValueBuilder(
                    value = "1.5",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder().build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "2.0",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "value2"
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "3.0",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "value3"
                ).build()
            )
        ).build(), response)
    }

    @Test
    fun `request all values for two months sorted by descending order`() {
        val resourceUrl = generateResourceUrl(
            from = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            to = LocalDateTime.of(2023, 3, 1, 0, 0, 0),
            sort = SortOrder.DESCENDING
        )
        val response = restTemplate.getForObject(resourceUrl, GetSensorValuesResponse::class.java)
        assertEquals(GetSensorValuesResponseBuilder(
            request = GetSensorValuesRequestParametersBuilder(
                valueType = null,
                sort = SortOrder.DESCENDING
            ).build(),
            valueList = listOf(
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "3.0",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "value3"
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "2.0",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    timestamp = OffsetDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
                    value = "value2"
                ).build(),
                GetSensorValuesSensorMessageValueBuilder(
                    value = "1.5",
                    valueType = ValueType.NUMERICAL
                ).build(),
                GetSensorValuesSensorMessageValueBuilder().build()
            )
        ).build(), response)
    }

    @Test
    fun `request values for non-existent sensor returns empty values`() {
        val resourceUrl = generateResourceUrl(
            "unknownSensor",
            from=LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            to=LocalDateTime.of(2023, 3, 1, 0, 0, 0),
            valueType=ValueType.STRING
        )

        val response = restTemplate.getForObject(resourceUrl, GetSensorValuesResponse::class.java)
        assertEquals(GetSensorValuesResponseBuilder(
            request = GetSensorValuesRequestParametersBuilder(
                sensorId = "unknownSensor"
            ).build(),
            valueList = emptyList()
        ).build(), response)
    }

    @Test
    fun `request values for with very large timestamps throws 400`() {
        val resourceUrl = generateResourceUrl(
            from=LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            to=LocalDateTime.of(2024, 1, 1, 0, 0, 0)
        )

        val response = restTemplate.getForEntity(resourceUrl, String::class.java)
        assertTrue(response.statusCode.is4xxClientError)
    }

    @Test
    fun `request values without path parameters returns values for the last month`() {
        val response = restTemplate.getForObject(generateResourceUrl(), GetSensorValuesResponse::class.java)
        assertEquals(null, response.request.valueType)
        assertEquals(SortOrder.ASCENDING, response.request.sort)
        assertTrue(response.request.to < LocalDateTime.now() && response.request.to > LocalDateTime.now().minusMinutes(1))
        assertTrue(Duration.between(response.request.to.minusMonths(1), response.request.from) < Duration.ofSeconds(1))
        assertTrue(response.values.isEmpty())
    }

    private fun generateResourceUrl(
        sensorId: String = "sensorId",
        from: LocalDateTime? = null,
        to: LocalDateTime? = null,
        sort: SortOrder? = null,
        valueType: ValueType? = null
    ): String {
        var baseUrl = "http://localhost:$port/$sensorId/values?"
        if (from != null) baseUrl += "from=$from&"
        if (to != null) baseUrl += "to=$to&"
        if (sort != null) baseUrl += "sort=$sort&"
        if (valueType != null) baseUrl += "valueType=$valueType&"
        return baseUrl
    }

}
