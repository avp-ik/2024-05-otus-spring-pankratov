FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY auth-gateway-service/target/auth-gateway-service*.jar /opt/app/auth-gateway-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/opt/app/auth-gateway-service.jar"]