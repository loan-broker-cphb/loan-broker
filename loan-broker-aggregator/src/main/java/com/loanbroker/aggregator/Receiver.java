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
    private final MessageConverter converter;

    public Receiver(RabbitTemplate rabbitTemplate, MessageConverter converter) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
    }

    public void handleMessage(NormalizerAggregatorMessage message) {
//        NormalizerAggregatorMessage normalizerAggregator = (NormalizerAggregatorMessage) converter.fromMessage(message);
        System.out.println(message.getSsn());
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
