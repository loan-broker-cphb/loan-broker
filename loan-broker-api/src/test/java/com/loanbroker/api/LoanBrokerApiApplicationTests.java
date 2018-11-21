package com.loanbroker.api;

import com.loanbroker.api.resources.QuoteResponse;
import com.loanbroker.commons.model.QuoteRequest;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoanBrokerApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(classes = {TestConfig.class})
@AutoConfigureEmbeddedDatabase
@FlywayTest
public class LoanBrokerApiApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ReceiverStub receiver;

    @LocalServerPort
    protected int port;

    protected String baseUrl = "http://localhost";

    @Before
    public void setup() {
        this.baseUrl = baseUrl.concat(":").concat(Integer.toString(port));
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void postQuoteTest() {
        QuoteRequest request = new QuoteRequest();
        request.setSsn("111192-1234");
        request.setLoanAmount(BigDecimal.valueOf(70000));
        request.setLoanDuration(100);
        QuoteResponse response = restTemplate.postForObject(this.baseUrl + "/quote", request, QuoteResponse.class);
        Assert.assertEquals(request.getSsn(), response.getSsn());
    }

    @Test
    public void quoteOnQueueTest() throws InterruptedException {
        postQuoteTest();
        Thread.sleep(1000);
        List<QuoteRequest> requests = receiver.getMessageList();
        Assert.assertTrue(requests.size() > 0);
        Assert.assertEquals("111192-1234", requests.get(0).getSsn());
    }

}
