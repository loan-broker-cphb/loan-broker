package com.loanbroker.normalizer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LoanBrokerNormalizerApplicationTests {

    @Test
    public void initialTest() {
        Assert.assertTrue(true);
    }

}
