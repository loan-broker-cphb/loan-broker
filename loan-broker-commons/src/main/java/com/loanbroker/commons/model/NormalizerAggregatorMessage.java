package com.loanbroker.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class NormalizerAggregatorMessage implements Serializable {
    public NormalizerAggregatorMessage() {}

    private int ssn;
    private BigDecimal interestRate;
    private Bank bank;

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
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
