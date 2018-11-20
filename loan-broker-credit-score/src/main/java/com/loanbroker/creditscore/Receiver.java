package com.loanbroker.creditscore;

import com.loanbroker.commons.model.CreditScoreToGetBanksDto;
import com.loanbroker.commons.model.QuoteRequest;
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

    public void handleMessage(QuoteRequest message) {
        System.out.println("GetCreditScore received message:");
        System.out.println(message.getSsn());

        CreditScoreToGetBanksDto result = new CreditScoreToGetBanksDto();
        result.setLoanAmount(message.getLoanAmount());
        result.setLoanDuration(message.getLoanDuration());
        result.setSsn(message.getSsn());
        result.setCreditScore(getCreditScore(Integer.toString(message.getSsn())));
        rabbitTemplate.convertAndSend(LoanBrokerCreditScoreApplication.getBanksExchangeName, routingKey, result);
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
}
