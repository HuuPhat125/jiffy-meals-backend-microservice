FROM openjdk:21-jdk
MAINTAINER chikeucheka@gmail.com,sophyjelly718@gmail.com
ARG JAR_FILE=target/resturant-service.jar
WORKDIR /opt/app
COPY ${JAR_FILE} resturant-service.jar
ENTRYPOINT ["java","-jar","resturant-service.jar"]
