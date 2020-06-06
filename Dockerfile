FROM maven:3.6.3-jdk-11 as builder

WORKDIR /app

COPY . .

RUN mvn clean install

FROM openjdk:11.0.6-slim

ENV PORT 80
ENV MYSQL_PORT 3306
ENV MYSQL_HOST locahost
ENV MYSQL_USERNAME user
ENV MYSQL_PASSWORD pwd
ENV MYSQL_DATABASE db
ENV SECRET_KEY secret

WORKDIR /app

COPY --from=builder /app/target/api-0.0.1-SNAPSHOT.jar /app/api.jar

EXPOSE ${PORT}

CMD ["java", "-jar", "api.jar"]