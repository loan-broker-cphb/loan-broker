package com.loanbroker.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GetBanksToGateway implements Serializable {
    private String ssn;
    private int creditScore;
    private BigDecimal loanAmount;
    private int duration;

    private List<Bank> banks;

    public GetBanksToGateway() {
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }
}
