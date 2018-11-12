package com.loanbroker.normalizer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoanBrokerNormalizerApplication {

    static final String directExchangeName = "direct.test";

    private static final String jsonQueueName = "normalizer-json";
    private static final String xmlQueueName = "normalizer-xml";


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
    MessageConverter xmlConverter() {
        XmlMapper xmlMapper = new XmlMapper();
        return new Jackson2XmlMessageConverter(xmlMapper);
    }

    @Bean
    MessageListenerAdapter xmlListenerAdapter(XmlReceiver xmlReceiver) {
        return new MessageListenerAdapter(xmlReceiver, xmlConverter());
    }

    @Bean
    MessageListenerAdapter jsonListenerAdapter(JsonReceiver jsonReceiver) {
        return new MessageListenerAdapter(jsonReceiver);
    }

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerNormalizerApplication.class, args);
    }
}
