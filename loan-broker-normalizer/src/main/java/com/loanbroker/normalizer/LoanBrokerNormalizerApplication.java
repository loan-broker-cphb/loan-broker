package com.loanbroker.normalizer;

import com.loanbroker.normalizer.model.BankResponseMessage;
import com.loanbroker.utils.ConnectionFactoryBuilder;
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

    @Value("${bank.url}")
    private String bankUrl;

    @Bean
    ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder.create(bankUrl);
    }

    @Bean
    SimpleMessageListenerContainer xmlContainer(MessageListenerAdapter xmlListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addQueueNames(xmlQueueName);
        container.setMessageListener(xmlListenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer jsonContainer(MessageListenerAdapter jsonListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addQueueNames(jsonQueueName);
        container.setMessageListener(jsonListenerAdapter);
        return container;
    }

    @Bean
    ClassMapper bankResponseMessageClassMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(BankResponseMessage.class);
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.loanbroker.commons.model.BankMessage", BankResponseMessage.class);
        idClassMapping.put("com.loanbroker.bank.translator.xml.XmlBankMessage", BankResponseMessage.class);
        idClassMapping.put("com.loanbroker.bank.web.service.model.QuoteResponse", BankResponseMessage.class);
        idClassMapping.put("com.rabbitbank.MessageReceiver.MessageResponce", BankResponseMessage.class);
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
