# Use uma imagem base com Java
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /search

# Copie o arquivo JAR gerado para a imagem
COPY target/*.jar search-0.0.1-SNAPSHOT.jar

# Comando para executar a aplicação
CMD ["java", "-jar", "search-0.0.1-SNAPSHOT.jar"]
