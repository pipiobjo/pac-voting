#!/usr/bin/env bash

set -e

cd voting-assembly/
mvn clean package
cd target/voting-assembly-*-bin
STAGE_PROFILE=dev

mkdir -p logs
mkdir -p pids
java -Xmx256m -Xss16m -jar support-service-registry-*.jar >> logs/support-serviceRegistry.log & echo $! > pids/support-serviceRegistry.pid
#java -Xmx256m -Xss16m -jar support-config-*.jar >> logs/support-configServer & echo $! > pids/support-configServer.pid
java -Xmx256m -Xss16m -jar support-auth-server-*.jar >> logs/support-authServer.log & echo $! > pids/support-authServer.pid
java -Xmx256m -Xss16m -jar support-edge-server-*.jar >> logs/support-edgeServer.log & echo $! > pids/support-edgeServer.pid
java -Xmx128m -Xss16m -jar support-monitor*.jar >> logs/support-monitor.log & echo $! > pids/support-monitor.pid

### business services
java -Xmx128m -Xss16m -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n -jar -Dspring.profiles.active=$STAGE_PROFILE composite-service-*.jar >> logs/composite.log & echo $! > pids/composite.pid
java -Xmx128m -Xss16m -agentlib:jdwp=transport=dt_socket,server=y,address=8001,suspend=n -jar api-service-*.jar >> logs/api.log & echo $! > pids/api.pid
java -Xmx256m -Xss16m -agentlib:jdwp=transport=dt_socket,server=y,address=8002,suspend=n -jar -Dspring.profiles.active=$STAGE_PROFILE persistence-voting-*.jar  >> logs/persistence.log & echo $! > pids/persistence.pid

### ui
java -Xmx128m -Xss16m -jar voting-ui*.jar -P dev -Dspring.profiles.active=$STAGE_PROFILE >> logs/voting-ui.log & echo $! > pids/voting-ui.pid






