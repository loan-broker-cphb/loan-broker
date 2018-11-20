package com.loanbroker.bank.translator.rabbit;

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
        rabbitTemplate.convertAndSend(message, m -> {
            m.getMessageProperties().setReplyTo("g4.rabbit.reply-to");
            return m;
        });
    }
}
