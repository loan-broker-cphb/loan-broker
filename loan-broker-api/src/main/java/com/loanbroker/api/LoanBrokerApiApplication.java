package com.loanbroker.api;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.loanbroker.commons.db")
@EntityScan("com.loanbroker.commons.db")
public class LoanBrokerApiApplication {

    public static final String creditScoreQueue = "loanrequest.getcreditscore";

    @Bean
    Queue queue() {
        return new Queue(creditScoreQueue, true);
    }

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerApiApplication.class, args);
    }
}
