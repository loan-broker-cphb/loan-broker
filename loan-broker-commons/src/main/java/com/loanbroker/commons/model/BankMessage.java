package com.loanbroker.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankMessage implements Serializable {
    private int ssn;
    private int creditScore;
    private BigDecimal loanAmount;
    private int loanDuration;

    public BankMessage() {
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }
}
