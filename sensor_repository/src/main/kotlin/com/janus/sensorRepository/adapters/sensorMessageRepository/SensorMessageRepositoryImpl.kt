package com.janus.sensorRepository.adapters.sensorMessageRepository

import com.janus.sensorRepository.adapters.sensorMessageRepository.mapper.SensorNumericalMessageEntityRowMapper
import com.janus.sensorRepository.adapters.sensorMessageRepository.mapper.SensorStringMessageEntityRowMapper
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.toSensorNumericalMessage
import com.janus.sensorRepository.adapters.sensorMessageRepository.model.toSensorStringMessage
import com.janus.sensorRepository.domain.entity.SensorNumericalMessage
import com.janus.sensorRepository.domain.entity.SensorStringMessage
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.port.SensorMessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.RuntimeException
import java.time.OffsetDateTime

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

    override fun getSensorStringMessages(
        sensorName: String,
        from: OffsetDateTime,
        to: OffsetDateTime,
        sort: SortOrder
    ): List<SensorStringMessage> {
        logger.debug(
            "operation=getSensorStringMessages, message='fetching sensor {} string messages from {} to {} in {} order'",
            sensorName,
            from,
            to,
            sort
        )
        val query = """
            SELECT * FROM sensor_string_message
            WHERE sensor_id = '$sensorName'
            AND message_timestamp between '$from' and '$to'
            ORDER BY message_timestamp ${sort.value}, id
        """.trimIndent()
        val results =  jdbcTemplate.query(query, SensorStringMessageEntityRowMapper())
        return results.map { it.toSensorStringMessage() }
    }

    override fun getSensorNumericalMessages(
        sensorName: String,
        from: OffsetDateTime,
        to: OffsetDateTime,
        sort: SortOrder
    ): List<SensorNumericalMessage> {
        logger.debug(
            "operation=getSensorNumericalMessages, message='fetching sensor {} numerical messages from {} to {} in {} order'",
            sensorName,
            from,
            to,
            sort
        )
        val query = """
            SELECT * FROM sensor_numerical_message
            WHERE sensor_id = '$sensorName'
            AND message_timestamp between '$from' and '$to'
            ORDER BY message_timestamp ${sort.value}, id
        """.trimIndent()
        val results =  jdbcTemplate.query(query, SensorNumericalMessageEntityRowMapper())
        return results.map { it.toSensorNumericalMessage() }
    }

}

class SensorMessageSaveError(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)