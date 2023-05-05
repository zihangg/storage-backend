FROM debian:latest

# update packages and install Java
RUN apt-get update && apt-get install -y openjdk-17-jdk

# copy jar file
WORKDIR /app
COPY target/backend-1.0.0.jar backend.jar

# create non-root user
RUN addgroup --system --gid 1001 spring
RUN adduser --system --uid 1001 --group spring

# give permission to new user
RUN chown -R spring:spring /app

# switch user
USER spring:spring

# expose port
EXPOSE 8080

# start app
ENTRYPOINT ["java","-jar","/app/backend.jar"]