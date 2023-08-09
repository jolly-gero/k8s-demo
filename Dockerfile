## ---- version1 -----##

# Use official base image of Java Runtim
FROM openjdk:17-jdk
# Set volume point to /tmp
VOLUME /tmp
# Set application's JAR file
ARG JAR_FILE=target/*.jar
# Add the application's JAR file to the container
ADD ${JAR_FILE} app.jar
## Make port 8080 available to the world outside container
EXPOSE 8080
# Run the JAR file
ENTRYPOINT ["java","-jar","/app.jar"]
