package com.loanbroker.aggregator;

import com.loanbroker.commons.db.Result;
import com.loanbroker.commons.db.ResultRepository;
import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    @Autowired
    private ResultRepository repository;

    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public Receiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(NormalizerAggregatorMessage message) {
        System.out.println("Aggregator received message:");
        System.out.println(message.getSsn());
        System.out.println(message.getBank().toString());
        Result result = new Result();
        result.setSsn(message.getSsn());
        result.setBank(message.getBank().toString());
        result.setInterestRate(message.getInterestRate());
        repository.save(result);
    }
}
