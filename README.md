# echo-service
Test bed service for example code and configuration. Has slack integration.

## Compile
`./gradlew clean snapshot build jacocoTestReport jacocoRootReport publishToMavenLocal`

## Run locally
```
docker-machine restart default
docker-compose build && docker-compose up
```

The echo service should come up on port :6000, which you should be able to hit in a 2nd terminal like:
```
eval "$(docker-machine env default)"

curl http://docker:6000/echo/env | jq

{
  "PATH": "/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
  "HOSTNAME": "17e50fd9e30c",
  "JAVA_DEBIAN_VERSION": "8u66-b17-1~bpo8+1",
  "affinity:container": "=12e2313dc16a303c5ba031583c2d5856d1fc7e7915bdeb5dbfe7ffd79baefb16",
  "JAVA_HOME": "/usr/lib/jvm/java-8-openjdk-amd64",
  "CA_CERTIFICATES_JAVA_VERSION": "20140324",
  "OLDPWD": "/usr/service/echo-service-server",
  "PWD": "/usr/service",
  "JAVA_VERSION": "8u66",
  "LANG": "C.UTF-8",
  "HOME": "/home/notroot"
}
```
