package com.loanbroker.commons.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "results")
public class Result implements Serializable {
    @Id
    @Column(name = "SSN", nullable = false)
    private Long ssn;

    @Column(name = "BANK", nullable = false)
    private String bank;

    @Column(name = "INTEREST_RATE", nullable = false)
    private BigDecimal interestRate;

    public Result() {
    }

    public Long getSsn() {
        return ssn;
    }

    public void setSsn(Long ssn) {
        this.ssn = ssn;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
