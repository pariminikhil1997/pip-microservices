FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/pip-springboot-micro-aws-exe.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]