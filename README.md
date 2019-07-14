# echo-service
Test bed service for example code and configuration.

Demonstrates:
* AutoValue POJO
* AbstractModelTest
* -db GuiceSupportModule testing pattern
* DAO Mapper and Binder
* Guice wiring in the -server
* -sentinel testing pattern
* JAXRS Client Factories
* BasicAuth / Dropwizard-Peer authenticators
* Healthcheck on admin port :6001
* The "resource layer <-> service layer <-> dao layer" pattern

## Compiling

See the [General Purpose Dropwizard Development Instructions](https://upside-services.atlassian.net/wiki/spaces/ENG/pages/65929227/General+Purpose+Dropwizard+Development+Instructions) for compiling, testing, dockerizing instructions

## Docker Example
Run this service with docker on your localhost like:
```
mci -Pshade
cd echo-service-server
export ALPHA_ECHO_SERVICE_AWS_ACCESS=<the AWS access code for the ECHO_SERVICE user>
export ALPHA_ECHO_SERVICE_AWS_SECRET=<the AWS secret for the ECHO_SERVICE user>
docker-compose build && docker-compose up
```

Once docker is up and running, the server is accessible at http://docker:11000 with a required username and password, like:
```
export server="http://docker:7004"
export ct="Content-Type: application/json"
export auth="xxx get from secured source xxx"

curl  $server/echo/hello 
```

