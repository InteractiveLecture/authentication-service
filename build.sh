#!/bin/bash
./gradlew clean build
docker build -t openservice/authentication-service:latest .

if [ "${TRAVIS_PULL_REQUEST}" = "false" ] && [ "${TRAVIS_REPO_SLUG}" = "InteractiveLecture/authentication-service" ] ; then
  docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD" -e="$DOCKER_EMAIL"
  docker push openservice/authentication-service:latest
fi
