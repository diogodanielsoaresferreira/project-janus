package com.janus.sensorRepository.adapters.sensorMessageProcessor.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.io.IOException


@JsonTest
internal class SensorMessageEventDeserializationTest(
    @Autowired private val jsonParser: JacksonTester<SensorMessageEvent>
) {

    @ParameterizedTest
    @MethodSource("successfulSensorMessageEventGenerator")
    fun `deserialize SensorMessageEvent correctly`(expected: SensorMessageEvent, actual: String) {
        val jsonActual = jsonParser.parse(actual).`object`
        assertEquals(expected, jsonActual)
    }

    @ParameterizedTest
    @MethodSource("malformedSensorMessageEventGenerator")
    fun `exception when deserialize malformed SensorMessageEvent`(message: String) {
        assertThrows(IOException::class.java) { jsonParser.parse(message) }
    }

    companion object {
        @JvmStatic
        fun successfulSensorMessageEventGenerator() = listOf(
            Arguments.of(
                SensorMessageEventBuilder().build(),
                SensorMessageEventFileReader.readFile("default.json")
            ),

            Arguments.of(
                SensorMessageEventBuilder(valueType="numerical", value="2").build(),
                SensorMessageEventFileReader.readFile("numericalValue.json")),
        )

        @JvmStatic
        fun malformedSensorMessageEventGenerator() = listOf(
            Arguments.of(SensorMessageEventFileReader.readFile("malformedTimestamp.json")),
            Arguments.of(SensorMessageEventFileReader.readFile("noValueField.json"))
        )
    }

}