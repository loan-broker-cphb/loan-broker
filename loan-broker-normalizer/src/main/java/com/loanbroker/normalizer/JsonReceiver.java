package com.loanbroker.normalizer;

import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import com.loanbroker.normalizer.model.BankResponseMessage;
import com.loanbroker.normalizer.model.BankResponseMessageMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public void handleMessage(BankResponseMessage message) {
        NormalizerAggregatorMessage aggregatorMessage = BankResponseMessageMapper.toNormalizerAggregatorMessage(message);
        aggregatorMessage.setBank(NormalizerAggregatorMessage.Bank.CPHB_JSON);
        System.out.println(aggregatorMessage.getBank().toString());
        rabbitTemplate.convertAndSend(LoanBrokerNormalizerApplication.aggregatorExchangeName, routingKey, aggregatorMessage);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
