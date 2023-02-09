FROM adoptopenjdk/openjdk11

LABEL maintainer="Amr Khaled"

#RUN mvn clean package

#WORKDIR /workmotion

COPY ./target/employee-state-machine-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "./employee-state-machine-0.0.1-SNAPSHOT.jar"]