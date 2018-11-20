package com.loanbroker.bank.translator.xml;

import com.loanbroker.commons.model.BankMessage;
import com.loanbroker.utils.DateUtil;

public class BankMessageMapper {
    public static XmlBankMessage toXmlBankMessage(BankMessage fromMessage) {
        XmlBankMessage toMessage = new XmlBankMessage();
        toMessage.setCreditScore(fromMessage.getCreditScore());
        toMessage.setLoanAmount(fromMessage.getLoanAmount());
        String ssn = fromMessage.getSsn().replace("-", "");
        toMessage.setSsn(Integer.parseInt(ssn));
        toMessage.setLoanDuration(DateUtil.daysFromEpochToDate(fromMessage.getLoanDuration()));
        return toMessage;
    }
}
