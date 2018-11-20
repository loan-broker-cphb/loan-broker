package com.loanbroker.normalizer;

import com.loanbroker.commons.model.Bank;
import com.loanbroker.commons.model.NormalizerAggregatorMessage;
import com.loanbroker.normalizer.model.BankResponseMessage;
import com.loanbroker.normalizer.model.BankResponseMessageMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class XmlReceiver {

    private final RabbitTemplate rabbitTemplate;

    public XmlReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(BankResponseMessage message) {
        NormalizerAggregatorMessage aggregatorMessage = BankResponseMessageMapper.toNormalizerAggregatorMessage(message);
        aggregatorMessage.setBank(Bank.CPHB_XML);
        rabbitTemplate.convertAndSend(LoanBrokerNormalizerApplication.aggregatorQueueName, aggregatorMessage);
    }
}
