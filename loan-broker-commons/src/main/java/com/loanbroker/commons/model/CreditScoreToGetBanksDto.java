package com.loanbroker.commons.model;

public class CreditScoreToGetBanksDto extends QuoteRequest {

    private int creditScore;

    public CreditScoreToGetBanksDto() {
        super();
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }
}
