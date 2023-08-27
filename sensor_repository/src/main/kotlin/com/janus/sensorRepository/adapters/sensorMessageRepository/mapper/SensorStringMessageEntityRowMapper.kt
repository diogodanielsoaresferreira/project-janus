package com.janus.sensorRepository.adapters.sensorMessageRepository.mapper

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorStringMessageEntity
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.time.OffsetDateTime

class SensorStringMessageEntityRowMapper: RowMapper<SensorStringMessageEntity> {
    override fun mapRow(rs: ResultSet, rowNum: Int): SensorStringMessageEntity {
        return SensorStringMessageEntity(
            rs.getLong("id"),
            rs.getString("sensor_id"),
            rs.getObject("message_timestamp", OffsetDateTime::class.java),
            rs.getString("message_value")
        )
    }
}
