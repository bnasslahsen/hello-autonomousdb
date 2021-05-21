#!/bin/bash

set -a
source .env
set +a

export KUBECONFIG=$HOME/.kube/config

# Get Wallet
# shellcheck disable=SC2086
oci db autonomous-database generate-wallet --autonomous-database-id ${HELLOATP_DB_OCID} --file "${WALLET_NAME}.zip" --password ${WALLET_PASSWORD} --generate-type 'ALL'
unzip -o "${WALLET_NAME}.zip" -d "${WALLET_NAME}"

# ATP DB WALLET
kubectl delete secret "${APP_PREFIX}-wallet" --ignore-not-found=true
kubectl create secret generic "${APP_PREFIX}-wallet" --from-file="./${WALLET_NAME}/"

# OCIR SECRET
kubectl delete secret "${APP_PREFIX}-registry" --ignore-not-found=true
kubectl create secret docker-registry "${APP_PREFIX}-registry" --docker-server="${DOCKER_SERVER}" --docker-username="${DOCKER_USERNAME}" --docker-password="${DOCKER_PASSWORD}" --docker-email="badr.nass.lahsen@oracle.com"

# ATP CONFIG MAP
kubectl delete configmap "${APP_PREFIX}-config" --ignore-not-found=true
kubectl create configmap "${APP_PREFIX}-config" --from-file=../src/main/resources/application-common.yml --from-file=../src/main/resources/application-oci.yml

# DEPLOYMENT
kubectl delete deployment "${APP_PREFIX}-service" --ignore-not-found=true

sed -e "s|APP_PREFIX|${APP_PREFIX}|g; s|DOCKER_SERVER|${DOCKER_SERVER}|g; s|BUCKET_NAMESPACE|${BUCKET_NAMESPACE}|g; s|WALLET_NAME|${WALLET_NAME}|g" "${APP_PREFIX}-service.yaml" | kubectl apply -f -

kubectl get services "${APP_PREFIX}-service"
kubectl get pods

# Clean downloaded wallet
rm -rf "${WALLET_NAME}.zip" ${WALLET_NAME}