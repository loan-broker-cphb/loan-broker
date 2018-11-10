package com.loanbroker.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class NormalizerAggregator implements Serializable {
    public NormalizerAggregator() {}

    private String ssn;
    private BigDecimal interestRate;

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
