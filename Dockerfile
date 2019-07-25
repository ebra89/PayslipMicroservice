FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/payslip-Microservice.jar

ADD ${JAR_FILE} payslip-Microservice.jar

RUN mkdir /home/dir

VOLUME /home/dir

EXPOSE 8080

ENTRYPOINT ["java","-jar","payslip-Microservice.jar"]