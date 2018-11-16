package com.loanbroker.commons.model;

import com.loanbroker.utils.ConnectionFactoryBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

public class RabbitTemplateBuilder {

    private ConnectionFactory connectionFactory;
    private MessageConverter converter;
    private String exchange;

    private RabbitTemplateBuilder() {}

    public RabbitTemplateBuilder connectionUri(String uri) {
        this.connectionFactory = ConnectionFactoryBuilder.create(uri);
        return this;
    }

    public RabbitTemplateBuilder messageConverter(MessageConverter converter) {
        this.converter = converter;
        return this;
    }

    public RabbitTemplateBuilder exchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public RabbitTemplate build() {
        RabbitTemplate template = new RabbitTemplate();

        if (this.connectionFactory != null) {
            template.setConnectionFactory(this.connectionFactory);
        }

        if (this.converter != null) {
            template.setMessageConverter(this.converter);
        }

        if (this.exchange != null) {
            template.setExchange(this.exchange);
        }

        return template;
    }

    public static RabbitTemplateBuilder newBuilder() {
        return new RabbitTemplateBuilder();
    }

}
