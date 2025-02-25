# Usa una imagen base de Java
FROM openjdk:23-jdk

# Configura el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación
COPY target/prueba-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre la app (ajústalo si usas otro)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]