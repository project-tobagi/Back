# Base image
FROM openjdk:11-jre-slim

# Add Maintainer Info
LABEL maintainer="your-email@example.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# The jar file is copied from the target folder to the image
COPY target/project-tobagi.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]