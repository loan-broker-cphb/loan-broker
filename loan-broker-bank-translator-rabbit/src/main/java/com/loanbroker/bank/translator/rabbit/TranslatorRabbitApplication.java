package com.loanbroker.bank.translator.rabbit;

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
public class TranslatorRabbitApplication {

    private static final String queueName = "bank.rabbit.translator";
    private static final String exchangeName = "translator.exch";
    private static final String bankUri = "amqp://guest:guest@datdb.cphbusiness.dk:5672";
    private final String bankExchange = "cphbusiness.bankRabbit";


    @Bean
    RabbitTemplate rabbitTemplate(MessageConverter) {
        return RabbitTemplateBuilder.newBuilder()
                .connectionUri(bankUri)
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
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(TranslatorRabbitApplication.class, args);
    }
}
