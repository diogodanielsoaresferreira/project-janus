package com.janus.sensorRepository.adapters.sensorValuesController

import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesRequestParameters
import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesResponse
import com.janus.sensorRepository.adapters.sensorValuesController.model.GetSensorValuesSensorMessageValue
import com.janus.sensorRepository.domain.entity.SortOrder
import com.janus.sensorRepository.domain.entity.ValueType
import com.janus.sensorRepository.domain.port.GetSensorValuesUseCase
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RestController
class SensorValuesController(
    @Autowired private val useCase: GetSensorValuesUseCase
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val ninetyDaysMillis = 7776000000

    @GetMapping("/{sensorId}/values")
    fun getValues(
        @PathVariable
        sensorId: String,

        @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now().minusMonths(1)}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        from: LocalDateTime,

        @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        to: LocalDateTime,

        @RequestParam(required = false, defaultValue = "ASCENDING")
        sort: SortOrder,

        @RequestParam(required = false)
        valueType: ValueType?
    ): GetSensorValuesResponse {

        logger.info("operation=getValues, message='received request', request='getValues', " +
                "parameters=${mapOf(
                    "sensor_id" to sensorId,
                    "from" to from,
                    "to" to to,
                    "sort" to sort,
                    "value_type" to valueType
                )}"
        )

        val millisDifference = ChronoUnit.MILLIS.between(from, to)
        if (millisDifference > ninetyDaysMillis || millisDifference < 0) {
            logger.warn("operation=getValues, message='incorrect time interval from $from to $to'")
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect time interval")
        }

        val sensorValues =
            if (valueType != null) {
                useCase.execute(sensorId, from, to, sort, valueType)
            } else {
                useCase.execute(sensorId, from, to, sort)
            }

        return GetSensorValuesResponse(
            request = GetSensorValuesRequestParameters(
                sensorId = sensorId,
                from = from,
                to = to,
                sort = sort,
                valueType = valueType
            ),
            values = sensorValues.map {
                GetSensorValuesSensorMessageValue(
                    sensorId = it.sensorId,
                    timestamp = it.timestamp,
                    value = it.value,
                    valueType = it.valueType
                )
            }
        )
    }

}