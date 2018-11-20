package com.loanbroker.loanbrokergetbanks;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rulebase.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class RuleBaseClient {

    @Value("${getbanks.routingkey}")
    private String routingKey;

    private CountDownLatch latch = new CountDownLatch(1);

    private final RabbitTemplate rabbitTemplate;

    public RuleBaseClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(int creditScore, double loanAmount) {
        System.out.println("GetCreditScore received message:");
        System.out.println(creditScore);
        System.out.println(loanAmount);
        List<Bank> banks = getBanks(creditScore,loanAmount);
        rabbitTemplate.convertAndSend(LoanBrokerGetBanksApplication.recipListExchangeName, routingKey, banks);
    }

    public List<Bank> getBanks(int creditScore, double loanAmount){
        BanksPortService service = new BanksPortService();
        GetBanksResponse response = new GetBanksResponse();
        BanksPort port = service.getBanksPortSoap11();
        List<Bank> banks = new ArrayList();
        GetBanksRequest request = new GetBanksRequest();
        request.setCreditScore(creditScore);
        request.setLoanAmount(loanAmount);
        response = port.getBanks(request);
        banks = response.getBanks();
        for (Bank b:banks) {
            System.out.println("THE BANK: " + b.getName());
            System.out.println("THE ROUTING KEY: " + b.getRoutingkey());
        }
        return null;
    }

//    public static void main(String[] args) {
//        getBanks(600, 10000000.52);
//    }
}
