package com.loanbroker.aggregator;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(LoanBrokerAggregatorApplication.directExchangeName, "ping", "Hello from RabbitMQ!");
        rabbitTemplate.convertAndSend(LoanBrokerAggregatorApplication.directExchangeName, "ping2", "Hello from RabbitMQ 2!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
