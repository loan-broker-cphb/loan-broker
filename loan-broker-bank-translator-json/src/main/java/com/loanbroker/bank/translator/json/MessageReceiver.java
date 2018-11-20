package com.loanbroker.bank.translator.json;

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
        message.setSsn(message.getSsn().replace("-", ""));
        rabbitTemplate.convertAndSend(message, m -> {
            m.getMessageProperties().setContentType("application/json");
            m.getMessageProperties().setReplyTo("g4.json.reply-to");
            return m;
        });
    }
}
