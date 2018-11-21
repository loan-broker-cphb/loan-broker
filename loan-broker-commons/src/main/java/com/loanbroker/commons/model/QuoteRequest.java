package com.loanbroker.commons.model;

import com.loanbroker.commons.validator.SsnFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class QuoteRequest implements Serializable {

    @NotNull
    @SsnFormat
    private String ssn;

    @NotNull
    private BigDecimal loanAmount;

    @NotNull
    private int loanDuration;

    public QuoteRequest() {
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
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
