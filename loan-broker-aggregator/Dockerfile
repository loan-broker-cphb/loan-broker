FROM java:8
VOLUME /tmp
ADD target/loan-broker-aggregator-0.0.1-SNAPSHOT.jar app.jar
ADD src/main/resources/application.properties application.properties
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java" ,"-jar", "/app.jar"]
