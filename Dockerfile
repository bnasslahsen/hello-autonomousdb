FROM eclipse-temurin:11.0.18_10-jdk

WORKDIR /root/app
COPY . .
RUN ./mvnw clean package ; echo ok
RUN mkdir /root/.oci
RUN cd ./dependencies; ls -ltr;
RUN mv ./dependencies/* /root/.oci
RUN apt-get update && apt-get install -y unzip
RUN unzip -d /root/.oci/wallet /root/.oci/wallet.zip
ENV TNS_ADMIN=/root/oci/wallet
RUN rm ./src/main/resources/application-hardcoded.yml && rm ./src/main/resources/application-local.yml; 
EXPOSE 8080
ENTRYPOINT ["bash", "-c", "java -jar target/hello-autonomousdb-0.0.1-SNAPSHOT.jar --spring.config.location=./src/main/resources/"]
