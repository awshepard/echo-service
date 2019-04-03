FROM openjdk:8-jre-alpine

RUN apk upgrade && \
    apk update

RUN addgroup notroot && \
    adduser -D -S -h /var/cache/bootapp -s /sbin/nologin -G notroot notroot

WORKDIR /usr/service

ARG SERVICE_JAR=echo-service-server.jar
ADD target/${SERVICE_JAR} /usr/service/echo-service-server.jar
ADD src/main/resources/app_config.yml /usr/service/
ADD scripts/run-server.sh /usr/service/run-server.sh

EXPOSE 7004 7005

USER notroot
CMD ["/usr/service/run-server.sh"]
