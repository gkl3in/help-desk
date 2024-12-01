FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/*.jar
EXPOSE 8888
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker container run -p 8888:8888 --name helpdesk-config-server -e CONFIG_SERVER_URI=https://github.com/gkl3in/help-desk-configs -e CONFIG_SERVER_USERNAME=gkl3in -e CONFIG_SERVER_PASSWORD=KEY gabinhokleinn/helpdesk-config-server:latest