package com.loanbroker.normalizer;

import com.loanbroker.normalizer.model.BankResponseMessage;
import com.loanbroker.utils.ConnectionFactoryBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LoanBrokerNormalizerApplication {

    private static final String jsonQueueName = "g4.json.reply-to";
    private static final String xmlQueueName = "g4.xml.reply-to";
    private static final String rabbitQueueName = "g4.rabbit.reply-to";

    @Value("${bank.url}")
    private String banUrl;

    @Bean
    Queue jsonQueue() {
        return new Queue(jsonQueueName, true);
    }

    @Bean
    Queue xmlQueue() {
        return new Queue(xmlQueueName, true);
    }

    @Bean
    Queue rabbitQueue() {
        return new Queue(rabbitQueueName, true);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder.create(banUrl);
    }

    @Bean
    SimpleMessageListenerContainer xmlContainer(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter xmlListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(xmlQueueName);
        container.setMessageListener(xmlListenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer jsonContainer(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter jsonListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(jsonQueueName);
        container.setMessageListener(jsonListenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer rabbitContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(rabbitQueueName);
        return container;
    }

    @Bean
    ClassMapper bankResponseMessageClassMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(BankResponseMessage.class);
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.loanbroker.commons.model.BankMessage", BankResponseMessage.class);
        idClassMapping.put("com.loanbroker.bank.translator.xml.XmlBankMessage", BankResponseMessage.class);
        mapper.setIdClassMapping(idClassMapping);
        return mapper;
    }

    @Bean
    MessageConverter xmlConverter() {
        Jackson2XmlMessageConverter converter = new Jackson2XmlMessageConverter();
        converter.setClassMapper(bankResponseMessageClassMapper());
        return converter;
    }

    @Bean
    MessageConverter jsonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(bankResponseMessageClassMapper());
        return converter;
    }

    @Bean
    MessageListenerAdapter xmlListenerAdapter(XmlReceiver xmlReceiver) {
        return new MessageListenerAdapter(xmlReceiver, xmlConverter());
    }

    @Bean
    MessageListenerAdapter jsonListenerAdapter(JsonReceiver jsonReceiver) {
        return new MessageListenerAdapter(jsonReceiver, jsonConverter());
    }

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerNormalizerApplication.class, args);
    }
}
