package com.janus.sensorRepository.adapters.sensorMessageProcessor.model

import java.io.FileReader

object SensorMessageEventFileReader {
    fun readFile(fileName: String) =
        FileReader(ClassLoader.getSystemResource("sensorMessageEvent/$fileName").path).readText()
}