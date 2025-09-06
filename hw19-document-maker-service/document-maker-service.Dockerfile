FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY document-maker-service/target/document-maker-service*.jar /opt/app/document-maker-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/opt/app/document-maker-service.jar"]