FROM openjdk:8-slim-buster

RUN apt-get -y update
RUN mkdir -p /usr/share/man/man1/
RUN apt-get -y install git maven
RUN git clone https://github.com/sagaranin/wiki-stream.git
WORKDIR /wiki-stream

RUN mvn clean install
RUN cp target/*.jar /app.jar
RUN rm -rf /wiki-stream

ENTRYPOINT ["java", "-jar", "/app.jar"]
