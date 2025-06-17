FROM openjdk:21-jdk
ARG JAR_FILE=target/config-server.jar
WORKDIR /opt/app
COPY ${JAR_FILE} config-server.jar
ENTRYPOINT ["java","-jar","config-server.jar"]
