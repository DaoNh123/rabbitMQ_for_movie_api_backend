FROM maven:3.8.5-openjdk-17
VOLUME /tmp
COPY . .
RUN mvn clean package -Dmaven.test.skip

ENTRYPOINT ["java", "-jar", "/target/rabbitMQConsumer_05_01-0.0.1-SNAPSHOT.jar"]

