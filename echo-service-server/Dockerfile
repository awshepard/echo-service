FROM java:8-jre-alpine

RUN apk upgrade && \
    apk update

RUN addgroup notroot && \
    adduser -D -S -h /var/cache/bootapp -s /sbin/nologin -G notroot notroot

WORKDIR /usr/service

ARG SERVICE_JAR=echo-service-server.jar
ADD target/${SERVICE_JAR} /usr/service/echo-service-server.jar
ADD src/main/resources/app_config.yml /usr/service/

EXPOSE 7004 7005

# Environment variables that are needed for local dev with docker-compose
ENV DATADOG_FREQUENCY="1 minute"
ENV DATADOG_HOST="localhost"
ENV DATADOG_PORT="8125"
ENV DATADOG_TAGS='"tier:local", "service:echo"'

USER notroot
CMD ["/usr/bin/java", "-jar", "echo-service-server.jar", "server", "app_config.yml"]
