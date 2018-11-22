package com.loanbroker.normalizer;

import com.loanbroker.commons.model.RabbitTemplateBuilder;
import com.loanbroker.utils.ConnectionFactoryBuilder;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalRabbitConfig {
    @Value("${aggregator.amqp.url}")
    private String aggregatorAmqpUrl;

    static final String aggregatorQueueName = "aggregator";

    static final String exchange = "replyto.exch";
    static final String jsonQueueName = "g4.json.bank.reply-to";
    static final String rabbitQueueName = "g4.rabbit.bank.reply-to";

    @Bean
    ConnectionFactory localConnectionFactory() {
        return ConnectionFactoryBuilder.create(aggregatorAmqpUrl);
    }

    @Bean
    AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(localConnectionFactory());
    }

    @Bean
    Queue aggregatorQueue() {
        return new Queue(aggregatorQueueName, true);
    }

    @Bean
    Queue g4LocalQueue() {
        return new Queue(jsonQueueName);
    }

    @Bean
    Queue g4RabbitQueue() { return new Queue(rabbitQueueName); }

    @Bean
    SimpleMessageListenerContainer g4JsonContainer(MessageListenerAdapter g4JsonListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(localConnectionFactory());
        container.addQueueNames(jsonQueueName);
        container.setMessageListener(g4JsonListenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer g4RabbitContainer(MessageListenerAdapter g4RabbitListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(localConnectionFactory());
        container.addQueueNames(rabbitQueueName);
        container.setMessageListener(g4RabbitListenerAdapter);
        return container;
    }

    @Bean
    RabbitTemplate localRabbitTemplate() {
        return RabbitTemplateBuilder.newBuilder()
                .connectionUri(aggregatorAmqpUrl)
                .build();
    }

    @Bean
    MessageListenerAdapter g4JsonListenerAdapter(G4JsonReceiver g4JsonReceiver, MessageConverter jsonConverter) {
        return new MessageListenerAdapter(g4JsonReceiver, jsonConverter);
    }

    @Bean
    MessageListenerAdapter g4RabbitListenerAdapter(G4RabbitReceiver g4RabbitReceiver, MessageConverter jsonConverter) {
        return new MessageListenerAdapter(g4RabbitReceiver, jsonConverter);
    }
}
