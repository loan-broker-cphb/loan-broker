package com.loanbroker.bank.translator.xml;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class XmlBankMessage implements Serializable {
    private int ssn;
    private int creditScore;
    private BigDecimal loanAmount;
    private Date loanDuration;

    public XmlBankMessage() {
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

    public Date getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Date loanDuration) {
        this.loanDuration = loanDuration;
    }
}
