package com.janus.sensor_repository.domain.port

interface EventProcessor<T> {
    fun process(event: T)
}