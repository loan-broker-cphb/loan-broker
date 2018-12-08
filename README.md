### Group 4: _Loan Broker Project_
> _This serves as the documentation of our project in System Integration.
See the project description [here](https://github.com/datsoftlyngby/soft2018fall-si-teaching-material/blob/master/Project/Loan%20Broker%20Project.pdf)._

***
<b>Authors:</b>
- Mikkel Ziemer Højbjerg Jensen (cph-mj496)
- Cherry Rose Jimenez Semeña (cph-cs241)
- Diana Strele (cph-ds126)
- Mahnaz Karimi (cph-mk406)
***

## PROJECT DOCUMENTATION

> _Loan Broker Project is originated from the book Enterprise Integration Patterns, 
which takes a loan request and determine the best banks that could grant the user a loan
with corresponding interest rates. The request will be processed by independent components which are described in the components section._

#### Run the project
Running the project as specified, will start up all the components in the correct order.
You should have:
#### Prerequisites
- Java 8
- Maven
- Docker

#### How to run the project
1. Clone the project repository
2. `cd` into the base directory
3. Run `docker-compose pull`
4. Run `docker-compose up -d loan-broker-rulebase`
5. Run `mvn install -DskipTests`
6. Run `docker-compose up -d --build`

This will start up the rulebase, all the banks and the project itself.

### Loan Broker System Design

> _There are two ways of components integration that we used for the loan broker system, through SOAP/REST web service and messaging with RabbitMQ. 
We have implemented each component in Spring Boot which widely supports amqp, REST web service and SOAP web service. See the system design below wherein
it clearly shows which components uses messaging and which are integrated through web service._


![image](https://user-images.githubusercontent.com/16150075/49685058-275e2d80-fadd-11e8-9830-82edcab035ea.png)

### The Message Flow
1. Receive the consumer's loan quote request.
2. Obtain the credit score and history from credit agency.
3. Determine the most appropriate banks to contact.
4. Send a request to each selected bank.
5. Collect responses from each selected bank.
6. Determine the best response.
7. Pass the result back to the consumer.

### Loan Broker Components
- [Web Application/Client](https://github.com/loan-broker-cphb/loan-broker-web-app)
> a simple web application made in nodejs that can accept loan request and display the best quote.
- Credit Bureau
> a given external component that returns the credit score based on SSN from the service here: http://datdb.cphbusiness.dk:8080/CreditScoreService/CreditScoreService?wsdl 
- [Get Credit Score](https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-credit-score)
> consumes from the loanrequest queue, get the credit score from the credit bureau, and then sends the loan request to getbanks queue.
- [Rule Base](https://github.com/loan-broker-cphb/rule-base)
> an external SOAP web service that determines the most appropriate banks to contact. See the [documentation](https://github.com/loan-broker-cphb/rule-base).
- [Get Banks](https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-get-banks)
> communicates with the rule base to get the list of appropriate banks for the loan request.
- [Recipient List](https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-recipient-gateway/src/main/java/com/loanbroker/recipient/gateway)
> deligates the loan request to the listed banks' translators
- [JSON](https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-bank-translator-json) /
[XML](https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-bank-translator-xml/src/main/java/com/loanbroker/bank/translator/xml)
Translators
> translates the loan request to the banks preferred format
- Given Banks: cphbusiness.bankXML and cphbusiness.bankJSON (http://datdb.cphbusiness.dk:15672)
- Additional Banks: 
    - [group4.bankRABBIT](https://github.com/loan-broker-cphb/bank-rabbitMQ) - the bank that uses messaging and RabbitMQ
    - [group4.bankJSON](https://github.com/loan-broker-cphb/bank-web-service) - the bank that uses REST web service
- [Normalizer](https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-normalizer/src/main/java/com/loanbroker/normalizer)
> translate all the bank responses to a common format [NormalizerAggregatorMessage.java](https://github.com/loan-broker-cphb/loan-broker/blob/master/loan-broker-commons/src/main/java/com/loanbroker/commons/model/NormalizerAggregatorMessage.java)
- [Aggregator](https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-aggregator/src/main/java/com/loanbroker/aggregator)
> aggregates all the bank responses into one loan response.

### Bottlenecks
Since we haven't done full test coverage for all the components, there is still a risk of failure from one component to another which could make the user clueless. 
In situations for business reasoning like too low credit score for big loan amount, or way long/short loan duration are not being handled.

It is also unknown how much requests can the system handle, as we haven't focus on scalability. One component may take a while in processing a message, 
especially in cases that it also communicates with a third-party/external component like the credit bureau/rule base as an example. 
If it continuously receiving loan request while encountering a failure from the external component,
it will not get the expected response. It may just get stuck processing that current message or worse make the system crash.

### Testability
...to do

### Error Handling
We have set up an error page from the user's view as it catches the exceptions. On the other hand, we also added a validator that restricts the format of SSN that comes in to the server.

**SSN Validator**
```java
@Override
        public boolean isValid(String ssn, ConstraintValidatorContext constraintValidatorContext) {
            return ssn != null && ssn.matches("[0-3][0-9][0-1][1-9]\\d{2}-\\d{4}?[^0-9]*");
}
```
Source: https://github.com/loan-broker-cphb/loan-broker/tree/master/loan-broker-commons/src/main/java/com/loanbroker/commons/validator

**Frontend Error Page**

![image](https://user-images.githubusercontent.com/16150075/48817290-fc868200-ed45-11e8-82de-d15e58e22706.png)

### Internal Loan Broker processes
We have been using Spring Boot Java application which makes it easier to tie up all the components or start them individually.
Spring Boot has everything we need for this project, such as `amqp` and web services dependencies.

**pom.xml**
```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <modules>
        <module>loan-broker-credit-score</module>
        <module>loan-broker-get-banks</module>
        <module>loan-broker-recipient-gateway</module>
        <module>loan-broker-bank-translator-json</module>
        <module>loan-broker-bank-translator-xml</module>
        <module>loan-broker-normalizer</module>
        <module>loan-broker-utils</module>
        <module>loan-broker-commons</module>
        <module>loan-broker-aggregator</module>
        <module>loan-broker-api</module>
        <module>loan-broker-commons-db</module>
    </modules>
```

Reference:  https://spring.io/guides/gs/multi-module/ 

In addition, we have set up `docker-compose` file to start everything up including frontend in corresponding docker containers. See the "How to run the project" section above.

See here: [docker-compose.yml](https://github.com/loan-broker-cphb/loan-broker/blob/master/docker-compose.yml)

