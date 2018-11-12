package com.loanbroker.normalizer;

import com.loanbroker.normalizer.model.IncomingMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@Component
public class XmlReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public XmlReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(IncomingMessage message) {
        System.out.println(message.getSsn());
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
