package com.loanbroker.aggregator;

import com.loanbroker.commons.db.Result;
import com.loanbroker.commons.db.ResultRepository;
import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
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
        Optional<Result> result = repository.findById(message.getSsn());
        if (result.isPresent()) {
            Result res = result.get();
            if (res.getInterestRate().compareTo(message.getInterestRate()) > 0) {
                res.setBank(message.getBank().toString());
                res.setInterestRate(message.getInterestRate());
                repository.save(res);
            }
        }
    }
}
