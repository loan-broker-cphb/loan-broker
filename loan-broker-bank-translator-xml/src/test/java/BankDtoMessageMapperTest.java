import com.loanbroker.bank.translator.xml.BankMessageMapper;
import com.loanbroker.bank.translator.xml.XmlBankMessage;
import com.loanbroker.commons.model.BankMessage;
import com.loanbroker.utils.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class BankDtoMessageMapperTest {

    @Test
    public void testDateConmversion() {
        BankMessage message = new BankMessage();
        message.setCreditScore(10);
        message.setLoanAmount(new BigDecimal(100));
        message.setSsn("111195-1234");
        message.setLoanDuration(365 * 3 + 1);

        XmlBankMessage xmlBankMessage = BankMessageMapper.toXmlBankMessage(message);

        Assert.assertEquals("1973-01-01 01:00:00.0 CET", DateUtil.dateToString(xmlBankMessage.getLoanDuration()));
    }
}
