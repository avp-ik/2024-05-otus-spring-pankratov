FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY document-store-service/target/document-store-service*.jar /opt/app/document-store-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/document-store-service.jar"]