package com.loanbroker.normalizer.model;

import com.loanbroker.commons.model.NormalizerAggregatorMessage;

public class BankResponseMessageMapper {
    public static NormalizerAggregatorMessage toNormalizerAggregatorMessage(BankResponseMessage fromMessage) {
        NormalizerAggregatorMessage toMessage = new NormalizerAggregatorMessage();
        toMessage.setSsn(fromMessage.getSsn());
        toMessage.setInterestRate(fromMessage.getInterestRate());
        return toMessage;
    }
}
