#!/bin/sh

# The following script creates namespace and ocirsecret token from OCIRTOKEN previously created when login into OCIR
# Secret can be created as this as well: 
# kubectl -n kube-system create secret docker-registry autoscaler-secret --docker-server=yul.ocir.io --docker-username='axxborlsp4jb/k8s_service_user' --docker-password='YOUR_AUTH_TOKEN'
# secret/autoscaler-secret created

kubectl create namespace sampleapp
kubectl create secret generic samplespringboot-ocir-secret --from-file=.dockerconfigjson=/home/ubuntu/.docker/config.json --type=kubernetes.io/dockerconfigjson --namespace sampleocirsecret
