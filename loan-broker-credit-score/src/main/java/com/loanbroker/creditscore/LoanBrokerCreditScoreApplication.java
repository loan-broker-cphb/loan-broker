package com.loanbroker.creditscore;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoanBrokerCreditScoreApplication {

	static final String queueName = "loanrequest.getcreditscore";

	static final String getBanksQueue = "getcreditscore.getbanks";


	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}

	@Bean
	Queue getBanksQueue() {
		return new Queue(getBanksQueue, true);
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
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver);
	}

	public static void main(String[] args) {
		SpringApplication.run(LoanBrokerCreditScoreApplication.class, args);
	}
}
