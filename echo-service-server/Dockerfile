FROM java:8u66-jdk

WORKDIR /usr/service

ADD echo-service-server/build/distributions/echo-service-server.zip /usr/service/
RUN ["unzip", "/usr/service/echo-service-server.zip"]
RUN ["rm", "/usr/service/echo-service-server.zip"]
ADD src/main/resources/app_config.yml /usr/service/


# Create a non-root user to run the server
RUN groupadd -r notroot && useradd -r -g notroot notroot
USER notroot

EXPOSE 7004 7005

# Run the server
CMD ["/usr/service/echo-service-server/bin/echo-service-server", "server", "/usr/service/app_config.yml"]
