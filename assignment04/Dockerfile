FROM maven:3-jdk-8
VOLUME /tmp
ADD . /
RUN mvn package 
CMD java -jar target/assignment04-1.0-SNAPSHOT.jar
