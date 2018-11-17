package com.loanbroker.commons.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Loan  implements Serializable {

    private String ssn;
    private float loanAmount;
    private String loanDuration;
    private int creditScore;
    private ArrayList<String> rulebaseBanks; // ??? why there are 2 lists of banks
    private ArrayList<Bank> banks = new ArrayList<>();


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

    public void addBank(Bank bank) {
        banks.add(bank);
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


    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public void setBanks(ArrayList<Bank> banks) {
        this.banks = banks;
    }

}
