FROM openjdk:21-jdk
ARG JAR_FILE=target/eureka-server.jar
WORKDIR /opt/app
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java","-jar","eureka-server.jar"]
