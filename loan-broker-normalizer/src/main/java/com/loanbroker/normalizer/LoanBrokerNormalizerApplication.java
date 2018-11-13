package com.loanbroker.normalizer;

import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoanBrokerNormalizerApplication {

    static final String aggregatorExchangeName = "direct.test";

    private static final String jsonQueueName = "g4.json.reply-to";
    private static final String xmlQueueName = "g4.xml.reply-to";

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
    DirectExchange exchange() {
        return new DirectExchange(aggregatorExchangeName);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate();
        ConnectionFactory factory = ConnectionFactoryBuilder.create(aggregatorAmqpUrl);
        template.setConnectionFactory(factory);
        return template;
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
