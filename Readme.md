kubectl --namespace jenkins port-forward svc/jenkins 8080:8080

kubectl create secret docker-registry dockercred --docker-server=https://index.docker.io/v1/ --docker-username=sarthaksatish --docker-password=**\*** --docker-email=sarthak8055@gmail.com
