#!/bin/sh

# The following script deletes microservice from OKE

kubectl delete -f ../yaml/02_Deployment.yaml
kubectl delete -f ../yaml/03_Service.yaml
kubectl delete -f ../yaml/04_Ingress_Rule.yaml