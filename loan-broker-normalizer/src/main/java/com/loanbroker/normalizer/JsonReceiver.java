package com.loanbroker.normalizer;

import com.loanbroker.normalizer.model.IncomingMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@Component
public class JsonReceiver {

    @Value("${normalizer.routingkey}")
    private String routingKey;

    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public JsonReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(IncomingMessage message) {
        System.out.println(message.getSsn());
        // rabbitTemplate.convertAndSend(LoanBrokerNormalizerApplication.directExchangeName, routingKey, message);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
