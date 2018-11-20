package com.loanbroker.commons.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class QuoteRequest {

    @NotNull
    private int ssn;

    @NotNull
    private BigDecimal loanAmount;

    @NotNull
    private int loanDuration;

    public QuoteRequest() {
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
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
