#terraform apply -destroy -auto-approve
rm -rf .terraform .terraform.lock.hcl terraform.tfstate
terraform init
terraform plan
terraform apply -auto-approve
mv kubeconfig $HOME/.kube/config
cd ../deployment
./deploy-app.sh

