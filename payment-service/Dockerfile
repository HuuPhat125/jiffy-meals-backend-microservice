FROM openjdk:21-jdk
ARG JAR_FILE=target/payment-service.jar
WORKDIR /opt/app
COPY ${JAR_FILE} payment-service.jar
ENTRYPOINT ["java","-jar","payment-service.jar"]
