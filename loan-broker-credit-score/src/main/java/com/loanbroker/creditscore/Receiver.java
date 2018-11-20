package com.loanbroker.creditscore;

import com.loanbroker.commons.model.CreditScoreToGetBanksDto;
import com.loanbroker.commons.model.QuoteRequest;
import creditbureau.CreditScoreService;
import creditbureau.CreditScoreService_Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

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
        result.setCreditScore(getCreditScore(message.getSsn()));
        System.out.println(result.getCreditScore());
        rabbitTemplate.convertAndSend(LoanBrokerCreditScoreApplication.getBanksQueue, result);
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
