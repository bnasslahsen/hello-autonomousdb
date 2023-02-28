#!/bin/sh


if [ $# -lt 4 ]
  then
    echo "Not enough arguments. Usage: ./build-tag-release.sh appname user region tenancy-hash"
    echo "Example: ./build-tag-release.sh samplespringboot oracleidentitycloudservice/denny.alquinta@oracle.com sa-santiago-1 idlhjo6dp3bd"
    exit 1

else
export APP_NAME=$1
export REGION=$3.ocir.io
export TENANCY_HASH=$4
export OCIR_USER=$TENANCY_HASH/$2

  echo "Enter OCIR Token"
  read -r OCIR_PASSWORD   

  echo "--- login into OCIR ---"
  echo ${OCIR_PASSWORD} | docker login -u ${OCIR_USER} --password-stdin ${REGION}  

  if [ $? -eq 1 ]
   then  
    echo "--- incorrect credentials. Try again"
    exit 1
  else

    echo "--- pruning images ---"
     docker system prune -f -a

    echo "--- building image ${APP_NAME}:latest ---"
    docker build -t samplespringboot:latest ../../

    echo "--- tag image ${APP_NAME}:latest ---"
    docker tag ${APP_NAME}:latest ${REGION}/${TENANCY_HASH}/${APP_NAME}:latest

    echo "--- push image ${APP_NAME}:latest ---"
    docker push ${REGION}/${TENANCY_HASH}/${APP_NAME}:latest

    echo "--- all set! ---"
  fi

fi