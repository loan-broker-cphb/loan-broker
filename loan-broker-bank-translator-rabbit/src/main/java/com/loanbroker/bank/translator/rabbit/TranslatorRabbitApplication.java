package com.loanbroker.bank.translator.rabbit;

import com.loanbroker.commons.model.BankMessage;
import com.loanbroker.commons.model.RabbitTemplateBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TranslatorRabbitApplication {

    private static final String queueName = "bank.rabbit.translator";
    private static final String exchangeName = "translator.exch";
    private final String routingKey = "G4_RABBIT";

    @Value("${bank.amqp.uri}")
    private String bankUri;

    private final String bankQueueName = "g4.rabbit.bank";


    @Bean
    RabbitTemplate rabbitTemplate(MessageConverter jsonConverter) {
        return RabbitTemplateBuilder.newBuilder()
                .connectionUri(bankUri)
                .messageConverter(jsonConverter)
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
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.addQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver);
    }

    @Bean
    MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    ClassMapper jsonBankMessageClassMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(BankMessage.class);
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.loanbroker.commons.model.BankMessage", BankMessage.class);
        mapper.setIdClassMapping(idClassMapping);
        return mapper;
    }

    @Bean
    MessageConverter fromJsonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(jsonBankMessageClassMapper());
        return converter;
    }
    public static void main(String[] args) {
        SpringApplication.run(TranslatorRabbitApplication.class, args);
    }
}
