package com.loanbroker.normalizer;

import com.loanbroker.commons.model.RabbitTemplateBuilder;
import com.loanbroker.normalizer.model.BankResponseMessage;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    static final String aggregatorExchangeName = "direct.test";

    private static final String jsonQueueName = "g4.json.reply-to";
    private static final String xmlQueueName = "g4.xml.reply-to";
    private static final String rabbitQueueName = "g4.rabbit.reply-to";

    @Value("${aggregator.amqp.url}")
    private String aggregatorAmqpUrl;

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
    DirectExchange exchange() {
        return new DirectExchange(aggregatorExchangeName);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        return RabbitTemplateBuilder.newBuilder()
                .connectionUri(aggregatorAmqpUrl)
                .build();
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
    SimpleMessageListenerContainer rabbitContainer(ConnectionFactory connectionFactory,
                                                MessageListenerAdapter) {
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
