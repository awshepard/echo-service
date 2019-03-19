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
* `mci` (aka `mvn clean install`) will run all unit and integration tests and install JARs in your local mvn repository

Compiling with test coverage is included in the `mci` alias and will produce reports in:

FIXME UP-3299 - come up with a jacoco-combining configuration in upside-parent-pom instead of this

`open */target/site/jacoco-combined/index.html`

## Docker
To create a docker image, activate the `shade` and `docker` plugins:
* `mci -Pshade,docker`

To push the docker image to our AWS ECR repository run:
* `$(aws ecr get-login --no-include-email --region us-east-1)`   # log in to our AWS ECR repo for 12 hours
* `mvn deploy -Pshade,docker`

Run this service with docker on your localhost like:
```
mci -Pshade
cd echo-service-server
export ALPHA_ECHO_SERVICE_AWS_ACCESS=<the AWS access code for the ECHO_SERVICE user>
export ALPHA_ECHO_SERVICE_AWS_SECRET=<the AWS secret for the ECHO_SERVICE user>
docker-compose build && docker-compose up

# NOTE: the very first time you bring docker up, the server image might "beat" the mysql image and not be able to connect to the mysql image because it's still boostrapping.
#      Once mysql is up, "Ctrl+C" the docker compose process and run docker-compose up again
```

Once docker is up and running, the server is accessible at http://docker:11000 with a required username and password, like:
```
export server="http://docker:7004"
export ct="Content-Type: application/json"
export auth="xxx get from secured source xxx"

curl  $server/echo/hello 
```

