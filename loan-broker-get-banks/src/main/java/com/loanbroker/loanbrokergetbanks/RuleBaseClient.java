package com.loanbroker.loanbrokergetbanks;

import rulebase.Bank;
import rulebase.BankService;
import rulebase.BankService_Service;

import java.util.ArrayList;
import java.util.List;

public class RuleBaseClient {

    public static List<Bank> getBanks(int creditScore, double loanAmount){
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

    public static void main(String[] args) {
        getBanks(600, 10000000.52);
    }
}
