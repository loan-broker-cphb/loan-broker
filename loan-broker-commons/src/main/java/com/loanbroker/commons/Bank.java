package com.loanbroker.commons;

public class Bank {
    private String name;
    private float interestRate;
    private String ssn;

    public Bank(float interestRate, String ssn) {
        this.interestRate = interestRate;
        this.ssn = ssn;
    }

    public Bank(float interestRate) {
        this.interestRate = interestRate;
    }

    public Bank(String name, float interestRate, String ssn) {
        this.name = name;
        this.interestRate = interestRate;
        this.ssn = ssn;
    }

    public Bank() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

}
