FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY target/hw17-docker*.jar /opt/app/hw17-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/hw17-docker.jar"]