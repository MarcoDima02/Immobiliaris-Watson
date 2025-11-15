# ============================================
# Stage 1: Build con Maven
# ============================================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia i file di configurazione Maven per cache delle dipendenze
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia il codice sorgente e compila
COPY src ./src
RUN mvn clean package -DskipTests -B

# ============================================
# Stage 2: Runtime con JRE leggero
# ============================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia il JAR compilato dallo stage precedente
COPY --from=builder /app/target/residea-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta 8080
EXPOSE 8080

# Configurazione JVM per container
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Variabili ambiente per database (sovrascrivibili da docker-compose)
ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV DB_NAME=residea_db
ENV DB_USER=residea_user
ENV DB_PASS=ResideaP@ss

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Avvia l'applicazione
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
