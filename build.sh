#!/bin/bash
./gradlew clean build
docker build -t openservice/authentication-service:latest .

