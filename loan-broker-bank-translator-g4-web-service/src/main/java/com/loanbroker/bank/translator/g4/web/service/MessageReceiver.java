package com.loanbroker.bank.translator.g4.web.service;

import com.loanbroker.commons.model.BankMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessageReceiver {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bank.baseurl}")
    private String bankBaseUrl;


    public void handleMessage(BankMessage message) {
        // TODO: Make http request to rest service
        message.setSsn(message.getSsn().replace("-", ""));
        QuoteRequestDto dto = new QuoteRequestDto();
        dto.setCreditScore(message.getCreditScore());
        dto.setLoanAmount(message.getLoanAmount().doubleValue());
        dto.setLoanDuration(message.getLoanDuration());
        dto.setSsn(Integer.parseInt(message.getSsn().replace("-", "")));
        dto.setReplyTo("g4.json.bank.reply-to");
        restTemplate.postForLocation(bankBaseUrl + "/quote", dto);
    }
}
