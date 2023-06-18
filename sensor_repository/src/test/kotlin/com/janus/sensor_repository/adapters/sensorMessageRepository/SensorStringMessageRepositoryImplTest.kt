package com.janus.sensor_repository.adapters.sensorMessageRepository

import com.janus.sensor_repository.domain.entity.SensorStringMessageBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.test.annotation.DirtiesContext
import java.sql.ResultSet
import java.sql.SQLException
import java.time.OffsetDateTime


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class SensorStringMessageRepositoryImplTest(
    @Autowired private val sensorStringRepository: SensorStringMessageRepositoryImpl,
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    @Test
    fun `save SensorStringMessage successfully`() {
        sensorStringRepository.save(SensorStringMessageBuilder().build())
        val count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_string_message", Long::class.java)
        val entity = jdbcTemplate.queryForObject("SELECT * FROM sensor_string_message", SensorMessageEntityRowMapper())
        assertEquals(1, count!!)
        assertEquals(SensorMessageEntityBuilder().build(), entity)
    }

    @Test
    fun `save duplicated SensorStringMessage successfully`() {
        sensorStringRepository.save(SensorStringMessageBuilder().build())
        sensorStringRepository.save(SensorStringMessageBuilder().build())
        val count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sensor_string_message", Long::class.java)
        val entity = jdbcTemplate.query("SELECT * FROM sensor_string_message", SensorMessageEntityRowMapper())
        assertEquals(2, count!!)
        assertEquals(SensorMessageEntityBuilder().build(), entity[0])
        assertEquals(SensorMessageEntityBuilder(id=2).build(), entity[1])
    }


    private class SensorMessageEntityRowMapper : RowMapper<SensorMessageEntity> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): SensorMessageEntity {
            return SensorMessageEntity(
                rs.getLong("id"),
                rs.getString("sensor_id"),
                rs.getObject("message_timestamp", OffsetDateTime::class.java),
                rs.getString("message_value")
            )
        }
    }

}