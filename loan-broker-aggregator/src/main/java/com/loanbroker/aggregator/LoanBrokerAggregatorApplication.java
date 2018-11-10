package com.loanbroker.aggregator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoanBrokerAggregatorApplication {

    static final String directExchangeName = "direct.test";

    static final String queueName = "normalizer-aggregator";

    static final String queueName2 = "normalizer-aggregator-2";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    Queue queue2() {
        return new Queue(queueName2, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("ping");
    }

    @Bean
    Binding binding2(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("ping2");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(queueName, queueName2);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageConverter messageConverter() {
        XmlMapper xmlMapper = new XmlMapper();
        return new Jackson2XmlMessageConverter(xmlMapper);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, messageConverter());
    }

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerAggregatorApplication.class, args);
    }
}
