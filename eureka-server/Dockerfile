FROM openjdk:21-jdk
MAINTAINER chikeucheka@gmail.com,sophyjelly718@gmail.com
ARG JAR_FILE=target/eureka-server.jar
WORKDIR /opt/app
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java","-jar","eureka-server.jar"]
