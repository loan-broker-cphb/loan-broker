package com.loanbroker.normalizer.model;

import java.math.BigDecimal;

public class IncomingMessage {

    private int ssn;
    private BigDecimal interestRate;

    public IncomingMessage() {
    }

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
}
