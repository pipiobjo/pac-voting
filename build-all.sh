#!/usr/bin/env bash

set -e
MAVEN_OPTS="-U"
cd voting-parent;  		mvn clean deploy $MAVEN_OPTS; cd -
cd common-model-api;            mvn clean deploy $MAVEN_OPTS; cd -

cd api-service;          	mvn clean deploy $MAVEN_OPTS; cd -
cd composite-service;         	mvn clean deploy $MAVEN_OPTS; cd -
cd persistence-voting; 		mvn clean deploy $MAVEN_OPTS; cd -

cd support-auth-server;         mvn clean deploy $MAVEN_OPTS; cd -
cd support-config;              mvn clean deploy $MAVEN_OPTS; cd -
cd support-edge-server;         mvn clean deploy $MAVEN_OPTS; cd -
cd support-monitor-dashboard;   mvn clean deploy $MAVEN_OPTS; cd -
cd support-service-registry;    mvn clean deploy $MAVEN_OPTS; cd -
cd support-turbine;             mvn clean deploy $MAVEN_OPTS; cd -

cd voting-web;			mvn clean deploy $MAVEN_OPTS; cd -

cd voting-assembly;		mvn clean package; cd -


find . -name *.jar -exec du -h {} \;
