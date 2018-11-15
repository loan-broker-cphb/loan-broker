package com.loanbroker.bank.translator.xml;

import com.loanbroker.commons.model.BankMessage;
import com.loanbroker.utils.DateUtil;

public class BankMessageMapper {
    public static XmlBankMessage toXmlBankMessage(BankMessage fromMessage) {
        XmlBankMessage toMessage = new XmlBankMessage();
        toMessage.setCreditScore(fromMessage.getCreditScore());
        toMessage.setLoanAmount(fromMessage.getLoanAmount());
        toMessage.setSsn(fromMessage.getSsn());
        toMessage.setLoanDuration(DateUtil.daysFromEpochToDate(fromMessage.getLoanDuration()));
        return toMessage;
    }
}
