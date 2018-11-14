package com.loanbroker.utils;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public class ConnectionFactoryBuilder {
    public static ConnectionFactory create(String uri) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri(uri);
        return connectionFactory;
    }
}
