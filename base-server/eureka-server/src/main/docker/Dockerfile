FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG eureka-server
ADD eureka-server.jar eureka-server.jar
EXPOSE 9001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","eureka-server.jar"]
