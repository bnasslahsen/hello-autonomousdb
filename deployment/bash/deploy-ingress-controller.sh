#!/bin/sh

# The following script deploys Ingress Controller Service over nginx on OCI via LBaaS

kubectl apply -f ../yaml/01_Ingress_Controller.yaml