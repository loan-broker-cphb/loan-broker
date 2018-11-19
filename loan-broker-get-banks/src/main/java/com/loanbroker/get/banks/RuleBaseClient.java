package com.loanbroker.get.banks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class RuleBaseClient extends WebServiceGatewaySupport {

    @Value("${wsdl.servie.url}")
    private String serviceUrl;

//    public GetBanksResponse getBanks(Loan loan) {
//        GetBanksRequest request = new GetBanksRequest();
//        request.setCreditScore(loan.getCreditScore());
//        request.setLoanAmount(BigDecimal.valueOf(loan.getLoanAmount()));
//
//        GetBanksResponse response = (GetBanksResponse) getWebServiceTemplate()
//                .marshalSendAndReceive(serviceUrl, request);
//    }
}
