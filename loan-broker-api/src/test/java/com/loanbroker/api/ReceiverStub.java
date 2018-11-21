package com.loanbroker.api;

import com.loanbroker.commons.model.QuoteRequest;

import java.util.ArrayList;
import java.util.List;

public class ReceiverStub {
    private final List<QuoteRequest> messageList = new ArrayList<>();

    public void handleMessage(QuoteRequest message) {
        messageList.add(message);
    }

    public List<QuoteRequest> getMessageList() {
        return messageList;
    }
}
