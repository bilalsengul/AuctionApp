FROM openjdk:17-jdk AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY . /workspace
RUN ./mvnw clean install

FROM openjdk:17-jdk as runtime
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]


