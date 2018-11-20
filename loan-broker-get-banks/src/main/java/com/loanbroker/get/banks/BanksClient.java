package com.loanbroker.get.banks;

import banks.wsdl.GetBanksRequest;
import banks.wsdl.GetBanksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class BanksClient extends WebServiceGatewaySupport {


    @Value("${getbanks.url}")
    private String getBanksBaseUrl;

    public GetBanksResponse getBanks(int creditScore, double loanAmount) {
        GetBanksRequest request = new GetBanksRequest();
        request.setCreditScore(creditScore);
        request.setLoanAmount(loanAmount);

        GetBanksResponse response = (GetBanksResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getBanksBaseUrl + "/ws/banks", request);

        return response;
    }

}
