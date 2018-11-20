package com.loanbroker.loanbrokergetbanks;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoanBrokerGetBanksApplication {

    static final String queueName = "getcreditscore.getbanks";

    static final String gatewayQueue = "gateway";


    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RuleBaseClient receiver) {
        return new MessageListenerAdapter(receiver);
    }

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerGetBanksApplication.class, args);
    }
}
