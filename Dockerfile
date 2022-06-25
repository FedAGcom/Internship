FROM openjdk:11

# copy the packaged jar file into our docker image
COPY target/internship-0.0.1-SNAPSHOT.jar /internship.jar

# set the startup command to execute the jar file
CMD ["java", "-jar", "/internship.jar"]