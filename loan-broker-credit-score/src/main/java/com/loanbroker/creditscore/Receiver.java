package com.loanbroker.creditscore;

import com.loanbroker.commons.model.Loan;
import creditbureau.CreditScoreService;
import creditbureau.CreditScoreService_Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    @Value("${creditscore.routingkey}")
    private String routingKey;

    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public Receiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(Loan message) {
        System.out.println("GetCreditScore received message:");
        System.out.println(message.getSsn());
        System.out.println(message.getCreditScore());
        message.setCreditScore(getCreditScore(message.getSsn()));
        rabbitTemplate.convertAndSend(LoanBrokerCreditScoreApplication.getBanksExchangeName, routingKey, message);
    }

    public int getCreditScore(String ssn){
        int creditScore = 0;
        try{
            CreditScoreService_Service service = new CreditScoreService_Service();
            CreditScoreService port = service.getCreditScoreServicePort();
            creditScore = port.creditScore(ssn);
        }catch(Exception e){
            System.out.println(e);
        }
        return creditScore;
    }

//        public static void main(String[] args) {
//        System.out.println(getCreditScore("110292-1234"));
//        System.out.println(getCreditScore("110292-1234"));
//        System.out.println(getCreditScore("110292-1234"));
//        System.out.println(getCreditScore("110292-1234"));
//    }
}
