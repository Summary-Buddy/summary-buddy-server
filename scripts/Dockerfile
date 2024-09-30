FROM ubuntu:22.04
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN apt install ffmpeg
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]