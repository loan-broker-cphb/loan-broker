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

## I. PROJECT DOCUMENTATION

> _Loan Broker Project was originated from the book Enterprise Integration Patterns, 
which basically takes a loan request and determine the best banks that could grant the user a loan
with corresponding interest rates. The request will be processed by independent components that is described further below._

#### Run this project
Running the project like specified will start up all the components in the correct order.
This also assumes that you have a 
#### Prerequisites
- Java 8
- Maven
- Docker

#### How to run the project
1. Clone the project
2. `cd` into the base directory
3. Run `mvn package`
4. Run `docker-compose up -d`

### Loan Broker Design
![image](https://user-images.githubusercontent.com/16150075/48804176-3343a480-ed14-11e8-8c38-01a4f67fe50a.png)

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

