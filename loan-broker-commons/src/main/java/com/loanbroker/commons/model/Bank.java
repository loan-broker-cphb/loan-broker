package com.loanbroker.commons.model;

public enum Bank {
    CPHB_XML("CPHB_XML"),
    CPHB_JSON("CPH_XML"),
    G4_JSON("G4_JSON"),
    G4_RABBIT("G4_RABBIT");

    private String value;

    Bank(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
