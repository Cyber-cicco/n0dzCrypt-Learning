#
# Package
#
FROM azul/zulu-openjdk-alpine:17-latest
WORKDIR /digi-learning
COPY *.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","app.jar"]

