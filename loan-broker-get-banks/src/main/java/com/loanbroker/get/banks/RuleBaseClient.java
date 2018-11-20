package com.loanbroker.get.banks;

import banks.wsdl.GetBanksResponse;
import com.loanbroker.commons.model.Bank;
import com.loanbroker.commons.model.CreditScoreToGetBanksDto;
import com.loanbroker.commons.model.GetBanksToGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleBaseClient {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    BanksClient client;

    public RuleBaseClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(CreditScoreToGetBanksDto creditScoreToGetBanksDto) {
        System.out.println("Get Banks received message:");
        System.out.println(creditScoreToGetBanksDto.getCreditScore());
        System.out.println(creditScoreToGetBanksDto.getLoanAmount());
        List<Bank> banks = getBanks(creditScoreToGetBanksDto.getCreditScore(), creditScoreToGetBanksDto.getLoanAmount().doubleValue());
        GetBanksToGateway getBanksToGateway = new GetBanksToGateway();
        getBanksToGateway.setBanks(banks);
        getBanksToGateway.setCreditScore(creditScoreToGetBanksDto.getCreditScore());
        getBanksToGateway.setDuration(creditScoreToGetBanksDto.getLoanDuration());
        getBanksToGateway.setLoanAmount(creditScoreToGetBanksDto.getLoanAmount());
        getBanksToGateway.setSsn(creditScoreToGetBanksDto.getSsn());

        rabbitTemplate.convertAndSend(LoanBrokerGetBanksApplication.gatewayQueue, getBanksToGateway);
    }

    public List<Bank> getBanks(int creditScore, double loanAmount){

        GetBanksResponse response = client.getBanks(creditScore, loanAmount);

        List<Bank> banks = new ArrayList<>();
        for (banks.wsdl.Bank b : response.getBanks()) {
            Bank bank = Bank.valueOf(b.getRoutingkey());
            banks.add(bank);
        }
        return banks;
    }

//    public static void main(String[] args) {
//        getBanks(600, 10000000.52);
//    }
}
