package com.janus.sensorRepository.adapters.sensorMessageRepository.mapper

import com.janus.sensorRepository.adapters.sensorMessageRepository.model.SensorNumericalMessageEntity
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.time.OffsetDateTime

class SensorNumericalMessageEntityRowMapper: RowMapper<SensorNumericalMessageEntity> {
    override fun mapRow(rs: ResultSet, rowNum: Int): SensorNumericalMessageEntity {
        return SensorNumericalMessageEntity(
            rs.getLong("id"),
            rs.getString("sensor_id"),
            rs.getObject("message_timestamp", OffsetDateTime::class.java),
            rs.getDouble("message_value")
        )
    }
}
