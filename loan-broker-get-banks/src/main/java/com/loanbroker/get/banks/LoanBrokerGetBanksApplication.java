package com.loanbroker.get.banks;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@SpringBootApplication
public class LoanBrokerGetBanksApplication {

    static final String queueName = "getcreditscore.getbanks";

    static final String gatewayQueue = "gateway";

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("banks.wsdl");
        return marshaller;
    }

    @Bean
    public BanksClient banksClient(Jaxb2Marshaller marshaller) {
        BanksClient client = new BanksClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    Queue gatewayQueue() {
        return new Queue(gatewayQueue, true);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RuleBaseClient receiver) {
        return new MessageListenerAdapter(receiver);
    }

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerGetBanksApplication.class, args);
    }
}
