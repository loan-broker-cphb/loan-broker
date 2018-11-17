package com.loanbroker.recipient.gateway;

import com.loanbroker.commons.model.Bank;
import com.loanbroker.commons.model.BankMessage;
import com.loanbroker.commons.model.GetBanksToGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    @Autowired
    RabbitTemplate template;

    private final String exchange = "translator.exch";

    public MessageReceiver() {
    }

    public void handleMessage(GetBanksToGateway message) {
        BankMessage bankMessage = new BankMessage();
        bankMessage.setLoanDuration(message.getDuration());
        bankMessage.setSsn(message.getSsn());
        bankMessage.setCreditScore(message.getCreditScore());
        bankMessage.setLoanAmount(message.getLoanAmount());
        for (Bank bank : message.getBanks()) {
            template.convertAndSend(exchange, bank.toString(), bankMessage);
        }
    }
}
