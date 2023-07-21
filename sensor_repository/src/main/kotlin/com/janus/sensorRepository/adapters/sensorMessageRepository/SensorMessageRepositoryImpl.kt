package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage
import com.janus.sensorRepository.domain.port.SensorMessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class SensorMessageRepositoryImpl(
    @Autowired private val jdbcTemplate: JdbcTemplate
): SensorMessageRepository {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun save(entity: SensorNumericalMessage) {
        val insertQuery = """
            INSERT INTO public.sensor_numerical_message(sensor_id, message_timestamp, message_value)
            VALUES (?, ?, ?)
        """.trimIndent()
        val rowsAffected = jdbcTemplate.update(insertQuery, entity.sensorId, entity.timestamp, entity.value)
        if (rowsAffected != 1) {
            logger.error("operation=save, message='error saving SensorNumericalMessage $entity', " +
                    "cause='Number of affected rows is different than 1: $rowsAffected'")
            throw SensorMessageSaveError("save SensorNumericalMessage $entity affected $rowsAffected rows")
        }
        logger.debug("operation=save, message='SensorNumericalMessage {} successfully saved'", entity)
    }

    override fun save(entity: SensorStringMessage) {
        val insertQuery = """
            INSERT INTO public.sensor_string_message(sensor_id, message_timestamp, message_value)
            VALUES (?, ?, ?)
        """.trimIndent()
        val rowsAffected = jdbcTemplate.update(insertQuery, entity.sensorId, entity.timestamp, entity.value)
        if (rowsAffected != 1) {
            logger.error("operation=save, message='error saving SensorStringMessage $entity', " +
                    "cause='Number of affected rows is different than 1: $rowsAffected'")
            throw SensorMessageSaveError("save SensorStringMessage $entity affected $rowsAffected rows")
        }
        logger.debug("operation=save, message='SensorStringMessage {} successfully saved'", entity)
    }

}

class SensorMessageSaveError(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)