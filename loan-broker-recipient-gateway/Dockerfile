FROM java:8
VOLUME /tmp
ADD target/loan-broker-recipient-gateway-1.0-SNAPSHOT.jar app.jar
ADD src/main/resources resources
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java" ,"-jar", "/app.jar"]
