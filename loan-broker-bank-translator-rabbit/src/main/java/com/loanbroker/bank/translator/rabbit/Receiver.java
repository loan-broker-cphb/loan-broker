package com.loanbroker.bank.translator.rabbit;

import com.loanbroker.commons.model.BankMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MessageConverter jsonConverter;

    public void handleMessage(BankMessage message) {
        QuoteRequestDto requestDto = new QuoteRequestDto();
        requestDto.setCreditScore(message.getCreditScore());
        requestDto.setLoanAmount(message.getLoanAmount().doubleValue());
        requestDto.setLoanDuration(message.getLoanDuration());
        requestDto.setSsn(message.getSsn());
        MessageProperties properties = new MessageProperties();
        properties.setReplyTo("g4.rabbit.bank.reply-to");
        properties.setContentType("application/json");
        Message rabbitMessage = jsonConverter.toMessage(requestDto, properties);
        rabbitMessage.getMessageProperties().getHeaders().remove("__TypeId__");
        rabbitTemplate.send("g4.rabbit.bank", rabbitMessage);
    }
}
