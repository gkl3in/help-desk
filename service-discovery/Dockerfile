FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/*.jar
EXPOSE 8761
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker container run -d -p 8761:8761 --name helpdesk-service-discovery -e CONFIG_SERVER_URI=http://localhost:8888 gabinhokleinn/helpdesk-service-discovery:latest