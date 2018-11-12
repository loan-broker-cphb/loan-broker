package com.loanbroker.normalizer;

import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoanBrokerNormalizerApplication {

    static final String directExchangeName = "direct.test";

    private static final String jsonQueueName = "normalizer-json";
    private static final String xmlQueueName = "normalizer-xml";
    private static final String aggregatorQueueName = "normalizer-aggregator";


    @Bean
    Queue jsonQueue() {
        return new Queue(jsonQueueName, true);
    }

    @Bean
    Queue xmlQueue() {
        return new Queue(xmlQueueName, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    Binding jsonQueueBinding(Queue jsonQueue, DirectExchange exchange) {
        return BindingBuilder.bind(jsonQueue).to(exchange).withQueueName();
    }

    @Bean
    Binding xmlQueueBinding(Queue xmlQueue, DirectExchange exchange) {
        return BindingBuilder.bind(xmlQueue).to(exchange).withQueueName();
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
    ClassMapper normalizerAggregatorMessageClassMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(NormalizerAggregatorMessage.class);
        return mapper;
    }

    @Bean
    MessageConverter xmlConverter() {
        Jackson2XmlMessageConverter converter = new Jackson2XmlMessageConverter();
        converter.setClassMapper(normalizerAggregatorMessageClassMapper());
        return converter;
    }

    @Bean
    MessageConverter jsonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(normalizerAggregatorMessageClassMapper());
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
