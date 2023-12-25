FROM gradle:7.6.1-jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM amazoncorretto:21.0.1-al2023-headless
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/sensor_repository-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]