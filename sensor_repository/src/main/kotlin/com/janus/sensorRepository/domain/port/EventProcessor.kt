package com.janus.sensorRepository.domain.port

interface EventProcessor<T> {
    fun process(event: T)
}