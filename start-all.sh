#!/usr/bin/env bash

set -e

cd voting-assembly/
mvn clean package
cd target/voting-assembly-*-bin
STAGE_PROFILE=development

mkdir -p logs
mkdir -p pids
java -jar support-service-registry-*.jar >> logs/support-serviceRegistry.log & echo $! > pids/support-serviceRegistry.pid
java -jar support-config-*.jar >> logs/support-configServer & echo $! > pids/support-configServer.pid
java -jar support-auth-server-*.jar >> logs/support-authServer.log & echo $! > pids/support-authServer.pid
java -jar support-edge-server-*.jar >> logs/support-edgeServer.log & echo $! > pids/support-edgeServer.pid
java -jar support-monitor*.jar >> logs/support-monitor.log & echo $! > pids/support-monitor.pid

java -jar persistence-voting-*.jar -Dspring.profiles.active=$STAGE_PROFILE >> logs/persistence.log & echo $! > pids/persistence.pid
java -jar composite-service-*.jar >> logs/composite.log & echo $! > pids/composite.pid
java -jar api-service-*.jar >> logs/api.log & echo $! > pids/api.pid




