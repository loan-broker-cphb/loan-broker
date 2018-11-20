package com.loanbroker.normalizer;

import com.loanbroker.commons.model.Bank;
import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import com.loanbroker.normalizer.model.BankResponseMessage;
import com.loanbroker.normalizer.model.BankResponseMessageMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class XmlReceiver {

    private final RabbitTemplate localRabbitTemplate;

    public XmlReceiver(RabbitTemplate localRabbitTemplate) {
        this.localRabbitTemplate = localRabbitTemplate;
    }

    public void handleMessage(BankResponseMessage message) {
        NormalizerAggregatorMessage aggregatorMessage = BankResponseMessageMapper.toNormalizerAggregatorMessage(message);
        aggregatorMessage.setBank(Bank.CPHB_XML);
        System.out.println(aggregatorMessage.getSsn());
        localRabbitTemplate.convertAndSend(LocalRabbitConfig.aggregatorQueueName, aggregatorMessage);
    }
}
