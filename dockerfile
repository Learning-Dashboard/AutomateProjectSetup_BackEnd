FROM openjdk:17-jdk

# Instalar dependencias necesarias para descargar y ejecutar Docker Compose
#RUN apt-get update && apt-get install -y curl
RUN mkdir -p /usr/local/bin
RUN curl -L "https://github.com/docker/compose/releases/download/v2.26.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose


#RUN curl -L "https://github.com/docker/compose/releases/download/v2.26.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

RUN chmod +x /usr/local/bin/docker-compose



COPY docker-compose.yml /home/run/
RUN chmod 777 /home/run/docker-compose.yml


COPY build/libs/automation-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
