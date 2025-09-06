FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY document-printer-service/target/document-printer-service*.jar /opt/app/document-printer-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/opt/app/document-printer-service.jar"]