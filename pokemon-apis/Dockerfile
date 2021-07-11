FROM ubuntu:latest

MAINTAINER Jiten Panchal "jiten.panchal@hotmail.co.uk"

ENV version=docker

RUN apt-get update && apt-get install -y openjdk-11-jdk

WORKDIR /usr/local/bin

ADD /target/pokemon-apis-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "pokemon-apis-0.0.1-SNAPSHOT.jar"]