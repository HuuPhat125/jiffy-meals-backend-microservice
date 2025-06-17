FROM openjdk:21-jdk
ARG JAR_FILE=target/order-service.jar
WORKDIR /app
COPY ${JAR_FILE} order-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","order-service.jar"]
