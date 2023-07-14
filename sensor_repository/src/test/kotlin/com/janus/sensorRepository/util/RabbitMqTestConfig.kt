package com.janus.sensorRepository.util

import com.rabbitmq.client.Channel
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.amqp.rabbit.connection.Connection
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.test.TestRabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqTestConfig {

    @Bean
    fun testRabbitTemplateFactory(): TestRabbitTemplate {
        return TestRabbitTemplate(connectionFactory())
    }

    fun connectionFactory(): ConnectionFactory {
        val factory: ConnectionFactory = Mockito.mock(ConnectionFactory::class.java)
        val connection: Connection = Mockito.mock(Connection::class.java)
        val channel: Channel = Mockito.mock(Channel::class.java)
        BDDMockito.willReturn(connection).given(factory).createConnection()
        BDDMockito.willReturn(channel).given(connection).createChannel(ArgumentMatchers.anyBoolean())
        BDDMockito.given(channel.isOpen).willReturn(true);
        return factory;
    }
}