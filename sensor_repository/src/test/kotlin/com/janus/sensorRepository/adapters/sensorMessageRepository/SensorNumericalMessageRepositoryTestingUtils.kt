package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.time.OffsetDateTime

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

class SensorNumericalMessageRepositoryTestingUtils(private val jdbcTemplate: JdbcTemplate) {

    fun getNumberOfElementsInRepository() =
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_numerical_message", Long::class.java)!!.toInt()

    fun getElementInRepository() =
        jdbcTemplate.queryForObject("SELECT * FROM sensor_numerical_message", SensorNumericalMessageEntityRowMapper())!!

    fun getElementsInRepository(): List<SensorNumericalMessageEntity> =
        jdbcTemplate.query("SELECT * FROM sensor_numerical_message", SensorNumericalMessageEntityRowMapper())
}