FROM eclipse-temurin:latest

COPY ./REST_API/target/REST_API-1.0-SNAPSHOT-jar-with-dependencies.jar ./app.jar

EXPOSE 5000

CMD ["java", "-jar", "app.jar"]

# docker build -t restapi .
# docker run -it --rm -p 7000:7000 --name rest restapi