package com.loanbroker.aggregator;

import com.loanbroker.commons.model.NormalizerAggregator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        NormalizerAggregator normalizerAggregator = new NormalizerAggregator();
        normalizerAggregator.setSsn("12345");
        normalizerAggregator.setInterestRate(BigDecimal.valueOf(1.12345));
        rabbitTemplate.convertAndSend(LoanBrokerAggregatorApplication.directExchangeName, "ping", normalizerAggregator);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
