# Use Tomcat with Java 21 (Matching your Eclipse project)
FROM tomcat:10.1-jdk21

# Remove default Tomcat apps to keep it clean
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file (We will create this in the next step)
COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]