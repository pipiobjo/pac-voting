#!/usr/bin/env bash

set -e
MAVEN_OPTS="-U"
cd voting-parent;  		mvn clean install $MAVEN_OPTS; cd -
cd common-model-api;            mvn clean install $MAVEN_OPTS; cd -

cd api-service;          	mvn clean install $MAVEN_OPTS; cd -
cd composite-service;         	mvn clean install $MAVEN_OPTS; cd -
cd persistence-voting; 		mvn clean install $MAVEN_OPTS; cd -

cd support-auth-server;         mvn clean install $MAVEN_OPTS; cd -
cd support-config;              mvn clean install $MAVEN_OPTS; cd -
cd support-edge-server;         mvn clean install $MAVEN_OPTS; cd -
cd support-monitor;   mvn clean install $MAVEN_OPTS; cd -
cd support-service-registry;    mvn clean install $MAVEN_OPTS; cd -

#cd voting-web;			mvn clean install $MAVEN_OPTS; cd -
cd voting-ui;			mvn clean install $MAVAN_OPTS -P dev -Dspring.profiles.active=dev; cd - 

cd voting-assembly;		mvn clean package; cd -


find . -name *.jar -exec du -h {} \;
