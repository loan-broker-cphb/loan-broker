package com.loanbroker.normalizer.model;


public class IncomingMessage {
    private String ssn;
    private String interestRate;

    public IncomingMessage() {
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }
}
