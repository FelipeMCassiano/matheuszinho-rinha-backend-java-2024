FROM gradle:jdk17-jammy AS build

COPY . .

RUN gradle build --no-daemon

CMD ["java", "-jar", "build/libs/backend-q1-0.0.1-SNAPSHOT.jar"]
