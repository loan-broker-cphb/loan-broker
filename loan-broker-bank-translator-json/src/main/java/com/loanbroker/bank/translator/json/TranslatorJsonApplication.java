package com.loanbroker.bank.translator.json;

import com.loanbroker.commons.model.BankMessage;
import com.loanbroker.commons.model.RabbitTemplateBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TranslatorJsonApplication {

    private static final String queueName = "bank.json.translator";
    private static final String exchangeName = "translator.exch";
    private static final String bankUri = "amqp://guest:guest@datdb.cphbusiness.dk:5672";
    private final String bankExchange = "cphbusiness.bankJSON";


    @Bean
    RabbitTemplate rabbitTemplate(MessageConverter jsonConverter) {
        return RabbitTemplateBuilder.newBuilder()
                .connectionUri(bankUri)
                .messageConverter(jsonConverter)
                .exchange(bankExchange)
                .build();
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.addQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, fromJsonConverter());
    }

    @Bean
    MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    ClassMapper jsonBankMessageClassMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(BankMessage.class);
        return mapper;
    }

    @Bean
    MessageConverter fromJsonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(jsonBankMessageClassMapper());
        return converter;
    }

    public static void main(String[] args) {
        SpringApplication.run(TranslatorJsonApplication.class, args);
    }
}