package com.loanbroker.loanbrokergetbanks;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rulebase.Bank;
import rulebase.BankService;
import rulebase.BankService_Service;

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
        BankService_Service service = new BankService_Service();
        BankService port = service.getBankServicePort();
        List<Bank> banks = new ArrayList();
        banks = port.makeLoan(creditScore, loanAmount);
        for (Bank b:banks) {
            System.out.println("THE BANK: " + b.getName());
            System.out.println("THE ROUTING KEY: " + b.getRoutingKey());
        }
        return null;
    }

//    public static void main(String[] args) {
//        getBanks(600, 10000000.52);
//    }
}
