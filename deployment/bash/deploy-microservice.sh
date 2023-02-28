#!/bin/sh

# The following script deploys Microservice into OKE

kubectl apply -f ../yaml/02_Deployment.yaml
kubectl apply -f ../yaml/03_Service.yaml
kubectl apply -f ../yaml/04_Ingress_Rule.yaml