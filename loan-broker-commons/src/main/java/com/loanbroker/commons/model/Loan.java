package com.loanbroker.commons.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Loan  implements Serializable {

    private String ssn;
    private float loanAmount;
    private String loanDuration;
    private int creditScore;
    private ArrayList<String> rulebaseBanks;
    private ArrayList<BankDto> bankDtos = new ArrayList<>();

    public Loan(String ssn, float loanAmount, String loanDuration) {
        this.ssn = ssn;
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
    }

    public Loan(String ssn, int creditScore, float loanAmount, String loanDuration) {
        this.ssn = ssn;
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
        this.creditScore = creditScore;
    }

    public Loan() {
    }


    public Loan(String ssn) {
        this.ssn = ssn;
    }

    public void addBank(BankDto bankDto) {
        bankDtos.add(bankDto);
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public float getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(float loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public ArrayList<String> getRulebaseBanks() {
        return rulebaseBanks;
    }

    public void setRulebaseBanks(ArrayList<String> rulebaseBanks) {
        this.rulebaseBanks = rulebaseBanks;
    }


    public ArrayList<BankDto> getBankDtos() {
        return bankDtos;
    }

    public void setBankDtos(ArrayList<BankDto> bankDtos) {
        this.bankDtos = bankDtos;
    }

}
