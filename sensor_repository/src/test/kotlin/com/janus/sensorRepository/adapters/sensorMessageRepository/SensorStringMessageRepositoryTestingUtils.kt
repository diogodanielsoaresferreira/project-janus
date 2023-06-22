package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.time.OffsetDateTime

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

class SensorStringMessageRepositoryTestingUtils(private val jdbcTemplate: JdbcTemplate) {

    fun getNumberOfElementsInRepository() =
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_string_message", Long::class.java)!!.toInt()

    fun getElementInRepository() =
        jdbcTemplate.queryForObject("SELECT * FROM sensor_string_message", SensorStringMessageEntityRowMapper())!!

    fun getElementsInRepository(): List<SensorStringMessageEntity> =
        jdbcTemplate.query("SELECT * FROM sensor_string_message", SensorStringMessageEntityRowMapper())
}