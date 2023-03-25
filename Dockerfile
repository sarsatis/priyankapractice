FROM khipu/openjdk17-alpine:latest
VOLUME /tmp
COPY /build/libs/priyankapractice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
