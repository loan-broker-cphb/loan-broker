package com.loanbroker.normalizer;

import com.loanbroker.commons.model.RabbitTemplateBuilder;
import com.loanbroker.utils.ConnectionFactoryBuilder;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalRabbitConfig {
    @Value("${aggregator.amqp.url}")
    private String aggregatorAmqpUrl;

    static final String aggregatorQueueName = "aggregator";

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
    RabbitTemplate localRabbitTemplate() {
        return RabbitTemplateBuilder.newBuilder()
                .connectionUri(aggregatorAmqpUrl)
                .build();
    }
}
