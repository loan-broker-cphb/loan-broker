package com.loanbroker.bank.translator.xml;

import com.loanbroker.commons.model.BankMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    private final RabbitTemplate rabbitTemplate;

    public MessageReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(BankMessage message) {
        rabbitTemplate.convertAndSend(BankMessageMapper.toXmlBankMessage(message), m -> {
            m.getMessageProperties().setContentType("application/xml");
            m.getMessageProperties().setReplyTo("g4.xml.reply-to");
            return m;
        });
    }
}
