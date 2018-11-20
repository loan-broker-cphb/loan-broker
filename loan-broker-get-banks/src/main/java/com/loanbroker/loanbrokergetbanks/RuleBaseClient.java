package com.loanbroker.loanbrokergetbanks;

import com.loanbroker.commons.model.Bank;
import com.loanbroker.commons.model.CreditScoreToGetBanksDto;
import com.loanbroker.commons.model.GetBanksToGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import rulebase.BankService;
import rulebase.BankService_Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleBaseClient {

    private final RabbitTemplate rabbitTemplate;

    public RuleBaseClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleMessage(CreditScoreToGetBanksDto creditScoreToGetBanksDto) {
        System.out.println("GetCreditScore received message:");
        System.out.println(creditScoreToGetBanksDto.getCreditScore());
        System.out.println(creditScoreToGetBanksDto.getLoanAmount());
        List<Bank> banks = getBanks(creditScoreToGetBanksDto.getCreditScore(), creditScoreToGetBanksDto.getLoanAmount().doubleValue());
        GetBanksToGateway getBanksToGateway = new GetBanksToGateway();
        getBanksToGateway.setBanks(banks);

        rabbitTemplate.convertAndSend(LoanBrokerGetBanksApplication.gatewayQueue, getBanksToGateway);
    }

    public List<Bank> getBanks(int creditScore, double loanAmount){
        BankService_Service service = new BankService_Service();
        BankService port = service.getBankServicePort();
        List<rulebase.Bank> rulebaseBanks = port.makeLoan(creditScore, loanAmount);
        List<Bank> banks = new ArrayList<>();
        for (rulebase.Bank b : rulebaseBanks) {
            Bank bank = Bank.valueOf(b.getRoutingKey());
            banks.add(bank);
        }
        return banks;
    }

//    public static void main(String[] args) {
//        getBanks(600, 10000000.52);
//    }
}
