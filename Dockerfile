FROM openjdk:13-alpine


COPY . /app
WORKDIR /app
EXPOSE 8080 

CMD ./gradlew bootrun

