#!/bin/sh

echo "********************************************************"
echo "Waiting for the Eureka to start on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! `nc -z eureka $EUREKASERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Eureka has started"

echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z config-service $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Starting zuul-service "
echo "********************************************************"
java \
-Djava.security.egd=file:/dev/./urandom \
-Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI \
-Dspring.cloud.config.uri=$CONFIGSERVER_URI \
-Dspring.profiles.active=$PROFILE \
-jar /usr/local/zuul/@project.build.finalName@.jar