package com.loanbroker.normalizer;

import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class XmlReceiver {

    @Value("${normalizer.routingkey}")
    private String routingKey;

    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public XmlReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(NormalizerAggregatorMessage message) {
        System.out.println(message.getInterestRate());
        rabbitTemplate.convertAndSend(LoanBrokerNormalizerApplication.aggregatorExchangeName, routingKey, message);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
