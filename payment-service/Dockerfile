FROM openjdk:21-jdk
MAINTAINER chikeucheka@gmail.com,sophyjelly718@gmail.com
ARG JAR_FILE=target/payment-service.jar
WORKDIR /opt/app
COPY ${JAR_FILE} payment-service.jar
ENTRYPOINT ["java","-jar","payment-service.jar"]
