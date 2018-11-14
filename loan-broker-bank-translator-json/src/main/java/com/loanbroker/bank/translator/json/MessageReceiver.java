package com.loanbroker.bank.translator.json;

import com.loanbroker.commons.model.JsonBankMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    private final RabbitTemplate rabbitTemplate;

    public MessageReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(JsonBankMessage message) {
        rabbitTemplate.convertAndSend(message, m -> {
            m.getMessageProperties().setContentType("application/json");
            m.getMessageProperties().setReplyTo("g4.json.reply-to");
            m.getMessageProperties().getHeaders().put("bank", "cphb-json");
            return m;
        });
    }
}
