FROM eclipse-temurin:17
WORKDIR /home
COPY ./target/guitars-inventory-service-0.0.1-SNAPSHOT.jar guitars-inventory-service.jar
ENTRYPOINT ["java", "-jar", "guitars-inventory-service.jar"]