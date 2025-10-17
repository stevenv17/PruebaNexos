# Etapa 1: construir usando Maven Wrapper
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia todo el proyecto, incluyendo mvnw y .mvn
COPY . .

# Da permisos de ejecución al script mvnw
RUN chmod +x mvnw

# Ejecuta el build usando Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Etapa 2: ejecutar el .jar
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia el .jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8089
CMD ["java", "-jar", "app.jar"]