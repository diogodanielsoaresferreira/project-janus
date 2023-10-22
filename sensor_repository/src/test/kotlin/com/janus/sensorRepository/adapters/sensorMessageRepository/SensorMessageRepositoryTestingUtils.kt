package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.mapper.SensorNumericalMessageEntityRowMapper
import com.janus.sensorRepository.adapters.sensorMessageRepository.mapper.SensorStringMessageEntityRowMapper
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntity
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntity
import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage
import org.springframework.jdbc.core.JdbcTemplate

class SensorMessageRepositoryTestingUtils(private val jdbcTemplate: JdbcTemplate) {

    fun insertSensorNumericalMessage(entity: SensorNumericalMessage) {
        val insertQuery = """
            INSERT INTO public.sensor_numerical_message(sensor_id, message_timestamp, message_value)
            VALUES (?, ?, ?)
        """.trimIndent()
        jdbcTemplate.update(insertQuery, entity.sensorId, entity.timestamp, entity.value)
    }

    fun insertSensorStringMessage(entity: SensorStringMessage) {
        val insertQuery = """
            INSERT INTO public.sensor_string_message(sensor_id, message_timestamp, message_value)
            VALUES (?, ?, ?)
        """.trimIndent()
        jdbcTemplate.update(insertQuery, entity.sensorId, entity.timestamp, entity.value)
    }

    fun getNumberOfNumericalMessages() =
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_numerical_message", Long::class.java)!!.toInt()

    fun getNumericalMessages(): List<SensorNumericalMessageEntity> =
        jdbcTemplate.query("SELECT * FROM sensor_numerical_message", SensorNumericalMessageEntityRowMapper())

    fun getNumberOfStringMessages() =
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_string_message", Long::class.java)!!.toInt()

    fun getStringMessages(): List<SensorStringMessageEntity> =
        jdbcTemplate.query("SELECT * FROM sensor_string_message", SensorStringMessageEntityRowMapper())

}
