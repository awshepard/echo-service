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

## Compile
`./gradlew clean snapshot build jacocoTestReport jacocoRootReport publishToMavenLocal`

## Run locally
```
docker-machine restart default
docker-compose build && docker-compose up

# note the first time you run "docker-compose up" the app might "beat" the mysql image in terms of coming up first, so the
# app will die because it can't connect to the DB (which is still bootstrapping itself the first time).  If this happens,
# just wait until the DB appears to be done bootstrapping, then "Ctrl+C" and try "docker-compose up" again
```

The echo service should come up on port :6000, which you should be able to hit in a 2nd terminal like:
```
eval "$(docker-machine env default)"

curl http://docker:6000/echo/hello

hello
```
