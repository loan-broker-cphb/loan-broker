package com.loanbroker.recipient.gateway;

import com.loanbroker.commons.model.GetBanksToGateway;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GatewayApplication {

    private static final String queueName = "gateway";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.addQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    ClassMapper bankResponseMessageClassMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(GetBanksToGateway.class);
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.loanbroker.commons.model.GetBanksToGateway", GetBanksToGateway.class);
        mapper.setIdClassMapping(idClassMapping);
        return mapper;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, jsonConverter());
    }

    @Bean
    MessageConverter jsonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(bankResponseMessageClassMapper());
        return converter;
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
