package com.loanbroker.normalizer.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankResponseMessage implements Serializable {

    private String ssn;
    private BigDecimal interestRate;

    public BankResponseMessage() {

    }

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
}
