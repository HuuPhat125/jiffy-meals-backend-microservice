FROM openjdk:21-jdk
ARG JAR_FILE=target/identity-service.jar
WORKDIR /opt/app
COPY ${JAR_FILE} identity-service.jar
ENTRYPOINT ["java","-jar","identity-service.jar"]
