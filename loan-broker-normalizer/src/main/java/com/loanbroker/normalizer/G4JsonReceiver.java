package com.loanbroker.normalizer;

import com.loanbroker.commons.model.Bank;
import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import com.loanbroker.normalizer.model.BankResponseMessage;
import com.loanbroker.normalizer.model.BankResponseMessageMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class G4JsonReceiver {

    private final RabbitTemplate localRabbitTemplate;

    public G4JsonReceiver(RabbitTemplate localRabbitTemplate) {
        this.localRabbitTemplate = localRabbitTemplate;
    }

    public void handleMessage(BankResponseMessage message) {
        System.out.println("G4 received message");
        NormalizerAggregatorMessage aggregatorMessage = BankResponseMessageMapper.toNormalizerAggregatorMessage(message);
        aggregatorMessage.setBank(Bank.G4_JSON);
        System.out.println(aggregatorMessage.getBank().toString());
        localRabbitTemplate.convertAndSend(LocalRabbitConfig.aggregatorQueueName, aggregatorMessage);
    }
}
