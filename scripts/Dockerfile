FROM ubuntu:22.04
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN apt-get update \
    apt install -y ffmpeg \
    apt install -y openjdk-21-jre-headless
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]