package com.loanbroker.aggregator;

import com.loanbroker.commons.model.NormalizerAggregator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public Receiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessage(NormalizerAggregator message) {
        System.out.println(message.getSsn());
        System.out.println(message.getInterestRate());
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
