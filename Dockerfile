# Use a imagem base do Maven com OpenJDK 21
FROM jelastic/maven:3.9.5-openjdk-21 AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e baixe as dependências do Maven
COPY pom.xml .

# Baixe as dependências do Maven (isso pode ser útil para cache de dependências)
# RUN mvn dependency:go-offline

# Copie o restante dos arquivos do projeto
COPY src /app/src

# Compile o projeto
CMD [ "mvn spring-boot:run" ]
