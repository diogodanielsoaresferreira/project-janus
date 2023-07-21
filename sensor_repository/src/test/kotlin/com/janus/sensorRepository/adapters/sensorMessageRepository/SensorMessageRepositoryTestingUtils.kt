package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntity
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.time.OffsetDateTime

class SensorMessageRepositoryTestingUtils(private val jdbcTemplate: JdbcTemplate) {

    fun getNumberOfNumericalMessages() =
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_numerical_message", Long::class.java)!!.toInt()

    fun getNumericalMessages(): List<SensorNumericalMessageEntity> =
        jdbcTemplate.query("SELECT * FROM sensor_numerical_message", SensorNumericalMessageEntityRowMapper())

    fun getNumberOfStringMessages() =
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_string_message", Long::class.java)!!.toInt()

    fun getStringMessages(): List<SensorStringMessageEntity> =
        jdbcTemplate.query("SELECT * FROM sensor_string_message", SensorStringMessageEntityRowMapper())

}

class SensorNumericalMessageEntityRowMapper: RowMapper<SensorNumericalMessageEntity> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): SensorNumericalMessageEntity {
        return SensorNumericalMessageEntity(
            rs.getLong("id"),
            rs.getString("sensor_id"),
            rs.getObject("message_timestamp", OffsetDateTime::class.java),
            rs.getDouble("message_value")
        )
    }
}

class SensorStringMessageEntityRowMapper: RowMapper<SensorStringMessageEntity> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): SensorStringMessageEntity {
        return SensorStringMessageEntity(
            rs.getLong("id"),
            rs.getString("sensor_id"),
            rs.getObject("message_timestamp", OffsetDateTime::class.java),
            rs.getString("message_value")
        )
    }
}
