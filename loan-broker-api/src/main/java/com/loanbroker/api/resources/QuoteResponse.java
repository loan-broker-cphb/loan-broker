package com.loanbroker.api.resources;

public class QuoteResponse {
    private String ssn;

    public QuoteResponse() {
    }

    public QuoteResponse(String ssn) {
        this.ssn = ssn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
