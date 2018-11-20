package com.loanbroker.normalizer.model;

import com.loanbroker.commons.model.NormalizerAggregatorMessage;

public class BankResponseMessageMapper {
    public static NormalizerAggregatorMessage toNormalizerAggregatorMessage(BankResponseMessage fromMessage) {
        NormalizerAggregatorMessage toMessage = new NormalizerAggregatorMessage();
        String ssn = fromMessage.getSsn().substring(0, 6) + "-" + fromMessage.getSsn().substring(6);
        toMessage.setSsn(ssn);
        toMessage.setInterestRate(fromMessage.getInterestRate());
        return toMessage;
    }
}
