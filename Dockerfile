FROM eclipse-temurin:23-jdk AS builder
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

WORKDIR /workspace
COPY . .
RUN mvn -T1C clean package -DskipTests

FROM eclipse-temurin:23-jre AS client-runtime
WORKDIR /app

COPY --from=builder /workspace/ClientProcessing/target/*.jar /app/app-client.jar
ENTRYPOINT ["sh", "-c", "java -jar /app/app-client.jar"]

FROM eclipse-temurin:23-jre AS account-runtime
WORKDIR /app
COPY --from=builder /workspace/AccountProcessing/target/*.jar /app/app-account.jar
ENTRYPOINT ["sh", "-c", "java -jar /app/app-account.jar"]

FROM eclipse-temurin:23-jre AS credit-runtime
WORKDIR /app
COPY --from=builder /workspace/CreditProcessing/target/*.jar /app/app-credit.jar
ENTRYPOINT ["sh", "-c", "java -jar /app/app-credit.jar"]
