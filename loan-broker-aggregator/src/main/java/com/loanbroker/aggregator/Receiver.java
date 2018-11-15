package com.loanbroker.aggregator;

import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public Receiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(NormalizerAggregatorMessage message) {
        System.out.println("Aggregator received message:");
        System.out.println(message.getSsn());
        System.out.println(message.getBank().toString());
    }
}
