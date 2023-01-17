FROM openjdk:17-jdk as builder
COPY . .
RUN ./mvnw clean install

FROM openjdk:17-jdk as runtime
COPY --from=builder target/*.jar app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar"]