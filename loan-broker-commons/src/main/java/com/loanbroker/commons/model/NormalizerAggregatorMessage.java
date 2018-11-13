package com.loanbroker.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class NormalizerAggregatorMessage implements Serializable {
    public NormalizerAggregatorMessage() {}

    private int ssn;
    private BigDecimal interestRate;

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
