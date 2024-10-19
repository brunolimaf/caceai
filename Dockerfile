# Etapa 1: Construção do JAR usando Maven
FROM maven:3.8.5-openjdk-17-slim AS builder

# Defina o diretório de trabalho
WORKDIR /search

# Copie os arquivos do projeto para a imagem
COPY . .

# Execute o Maven para construir o JAR
RUN mvn clean package

# Etapa 2: Criação da imagem final com Java
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /search

# Copie o arquivo JAR gerado da etapa de construção
COPY --from=builder /search/target/*.jar search-0.0.1-SNAPSHOT.jar

# Comando para executar a aplicação
CMD ["java", "-jar", "search-0.0.1-SNAPSHOT.jar"]
