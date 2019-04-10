FROM 337068080576.dkr.ecr.us-east-1.amazonaws.com/base/dropwizard:ci-0.0.1-1f4c992

ARG SERVICE_JAR=echo-service-server.jar
ADD target/${SERVICE_JAR} /usr/service/server.jar
ADD src/main/resources/app_config.yml /usr/service/
