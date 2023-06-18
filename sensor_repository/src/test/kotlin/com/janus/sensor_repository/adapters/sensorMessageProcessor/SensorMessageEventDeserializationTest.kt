package com.janus.sensor_repository.adapters.sensorMessageProcessor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.io.FileReader
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
                FileReader(ClassLoader.getSystemResource("sensorMessageEvent/default.json").path).readText()
            ),

            Arguments.of(
                SensorMessageEventBuilder(valueType="numerical", value="2").build(),
                FileReader(ClassLoader.getSystemResource("sensorMessageEvent/numericalValue.json").path).readText())
        )

        @JvmStatic
        fun malformedSensorMessageEventGenerator() = listOf(
            Arguments.of(
                FileReader(ClassLoader.getSystemResource("sensorMessageEvent/malformedTimestamp.json").path).readText()
            ),

            Arguments.of(
                FileReader(ClassLoader.getSystemResource("sensorMessageEvent/noValueField.json").path).readText())
        )
    }

}