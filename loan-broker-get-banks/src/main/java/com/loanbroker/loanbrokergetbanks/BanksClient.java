package com.loanbroker.loanbrokergetbanks;

import banks.wsdl.GetBanksRequest;
import banks.wsdl.GetBanksResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class BanksClient extends WebServiceGatewaySupport {


    public GetBanksResponse getBanks(int creditScore, double loanAmount) {
        GetBanksRequest request = new GetBanksRequest();
        request.setCreditScore(creditScore);
        request.setLoanAmount(loanAmount);

        GetBanksResponse response = (GetBanksResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/banks", request);

        return response;
    }

}
