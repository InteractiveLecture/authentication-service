#!/bin/bash

./gradlew clean build
docker build -t richterrettich/authentication-service:latest .
