FROM openjdk:21-jdk
ARG JAR_FILE=target/gateway-server.jar
WORKDIR /opt/app
COPY ${JAR_FILE} gateway-server.jar
ENTRYPOINT ["java","-jar","gateway-server.jar"]
