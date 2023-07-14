package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.port.SensorNumericalMessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class SensorNumericalMessageRepositoryImpl(
    @Autowired private val jdbcTemplate: JdbcTemplate
): SensorNumericalMessageRepository {

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
            throw SensorNumericalMessageSaveError("save SensorNumericalMessage $entity affected $rowsAffected rows")
        }
        logger.debug("operation=save, message='SensorNumericalMessage {} successfully saved'", entity)
    }

}

class SensorNumericalMessageSaveError(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)