FROM openjdk:21-jdk
MAINTAINER chikeucheka@gmail.com,sophyjelly718@gmail.com
ARG JAR_FILE=target/gateway-server.jar
WORKDIR /opt/app
COPY ${JAR_FILE} gateway-server.jar
ENTRYPOINT ["java","-jar","gateway-server.jar"]
