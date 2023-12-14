FROM eclipse-temurin:latest

COPY ./REST_API/out/artifacts/REST_API_jar/REST_API.jar ./app.jar

CMD ["java", "-jar", "app.jar"]

# docker build -t restapi .
# docker run -it --rm -p 7000:7000 --name rest restapi