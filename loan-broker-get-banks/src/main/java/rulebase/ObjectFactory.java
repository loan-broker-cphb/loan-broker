
package rulebase;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rulebase package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MakeLoan_QNAME = new QName("http://rulebase/", "makeLoan");
    private final static QName _MakeLoanResponse_QNAME = new QName("http://rulebase/", "makeLoanResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rulebase
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MakeLoan }
     * 
     */
    public MakeLoan createMakeLoan() {
        return new MakeLoan();
    }

    /**
     * Create an instance of {@link MakeLoanResponse }
     * 
     */
    public MakeLoanResponse createMakeLoanResponse() {
        return new MakeLoanResponse();
    }

    /**
     * Create an instance of {@link Bank }
     * 
     */
    public Bank createBank() {
        return new Bank();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeLoan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rulebase/", name = "makeLoan")
    public JAXBElement<MakeLoan> createMakeLoan(MakeLoan value) {
        return new JAXBElement<MakeLoan>(_MakeLoan_QNAME, MakeLoan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeLoanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rulebase/", name = "makeLoanResponse")
    public JAXBElement<MakeLoanResponse> createMakeLoanResponse(MakeLoanResponse value) {
        return new JAXBElement<MakeLoanResponse>(_MakeLoanResponse_QNAME, MakeLoanResponse.class, null, value);
    }

}
