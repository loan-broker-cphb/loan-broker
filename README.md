### Group 4: _Loan Broker Project_
> _This serves as the documentation of our project in System Integration.
See the project description [here](https://github.com/datsoftlyngby/soft2018fall-si-teaching-material/blob/master/Project/Loan%20Broker%20Project.pdf)._

***
<b>Authors:</b>
- Mikkel Ziemer Højbjerg Jensen (cph-mj496)
- Cherry Rose Semeña (cph-cs241)
- Diana Strele (cph-ds126)
- Mahnaz Karimi (cph-mk406)
***

### Loan Broker Design
![image](https://user-images.githubusercontent.com/16150075/47963816-60fcce00-e031-11e8-872c-cbd7dd077dd1.png)

### The Message Flow
1. Receive the consumer's loan quote request.
2. Obtain the credit score and history from credit agency.
3. Determine the most appropriate bankDtos to contact.
4. Send a request to each selected bankDto.
5. Collect responses from each selected bankDto.
6. Determine the best response.
7. Pass the result back to the consumer.


## Run this project
Running the project like specified will start up all the components in the correct order.
This also assumes that you have a 
### Prerequisites
- Java 8
- Maven
- Docker

### How to run the project
1. Clone the project
2. `cd` into the base directory
3. Run `mvn package`
4. Run `docker-compose up -d`