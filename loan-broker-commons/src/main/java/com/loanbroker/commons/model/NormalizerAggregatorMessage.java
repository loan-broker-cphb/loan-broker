package com.loanbroker.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class NormalizerAggregatorMessage implements Serializable {
    public NormalizerAggregatorMessage() {}

    private String ssn;
    private BigDecimal interestRate;
    private Bank bank;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

}
