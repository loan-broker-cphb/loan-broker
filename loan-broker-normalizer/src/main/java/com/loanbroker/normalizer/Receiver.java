package com.loanbroker.normalizer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public Receiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessage(String message) {
        System.out.println(message);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
