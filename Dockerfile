FROM java:8
RUN mkdir /lecture
WORKDIR /lecture
COPY build/distributions/authentication-service.tar authentication-service.tar
RUN tar -xf authentication-service.tar
EXPOSE 8080
ENTRYPOINT authentication-service/bin/authentication-service
