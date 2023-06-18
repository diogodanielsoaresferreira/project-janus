package com.janus.sensor_repository.adapters.sensorMessageRepository

import com.janus.sensor_repository.domain.entity.SensorStringMessage
import com.janus.sensor_repository.domain.port.SensorStringMessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class SensorStringMessageRepositoryImpl(
    @Autowired private val jdbcTemplate: JdbcTemplate
): SensorStringMessageRepository {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun save(entity: SensorStringMessage) {
        val insertQuery = """
            INSERT INTO public.sensor_string_message(sensor_id, message_timestamp, message_value)
            VALUES (?, ?, ?)
        """.trimIndent()
        val rowsAffected = jdbcTemplate.update(insertQuery, entity.sensorId, entity.timestamp, entity.value)
        if (rowsAffected != 1) {
            logger.error("operation=save, message='error saving SensorStringMessage $entity', " +
                    "cause='Number of affected rows is different than 1: $rowsAffected'")
            throw SensorStringMessageSaveError("Save SensorStringMessage $entity affected $rowsAffected rows")
        }
        logger.debug("operation=save, message='SensorStringMessage {} successfully saved'", entity)
    }

}

class SensorStringMessageSaveError(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)