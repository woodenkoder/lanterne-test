# Étape 1 : construire le jar avec le JDK et Maven.
FROM eclipse-temurin:21-jdk AS construction
WORKDIR /src
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw -B -q dependency:go-offline
COPY src src
RUN ./mvnw -B -q package -DskipTests

# Étape 2 : seulement le JRE et le jar, avec un utilisateur sans droits.
FROM eclipse-temurin:21-jre
RUN useradd --system --uid 10001 lanterne
WORKDIR /app
COPY --from=construction /src/target/lanterne-0.1.0.jar app.jar
USER lanterne
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
