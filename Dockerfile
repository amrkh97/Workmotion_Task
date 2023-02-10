FROM maven:3-jdk-11-slim AS build

WORKDIR /app

COPY . .

RUN mvn clean package

FROM maven:3-jdk-11-slim

LABEL maintainer="Amr Khaled"

COPY --from=build /app/target/employee-state-machine-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "./employee-state-machine-0.0.1-SNAPSHOT.jar"]