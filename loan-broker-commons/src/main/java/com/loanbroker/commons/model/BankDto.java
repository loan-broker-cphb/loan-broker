package com.loanbroker.commons.model;

public class BankDto {
    private String name;
    private float interestRate;
    private String ssn;

    public BankDto(float interestRate, String ssn) {
        this.interestRate = interestRate;
        this.ssn = ssn;
    }

    public BankDto(float interestRate) {
        this.interestRate = interestRate;
    }

    public BankDto(String name, float interestRate, String ssn) {
        this.name = name;
        this.interestRate = interestRate;
        this.ssn = ssn;
    }

    public BankDto() {
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
