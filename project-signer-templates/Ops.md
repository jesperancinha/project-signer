## DevOps References

```text
$ touch /etc/nginx/sites-available/mancalaje
$ sudo ln -s /etc/nginx/sites-available/mancalaje /etc/nginx/sites-enabled/
$ sudo service nginx restart
$ sudo vim /etc/systemd/system/mancalaje.service  
$ sudo vim mancalaje-service  
$ sudo chmod +x mancalaje-service  
$ sudo systemctl daemon-reload  
$ sudo systemctl enable mancalaje  
$ sudo systemctl start mancalaje  
$ sudo systemctl status mancalaje
$ sudo -u postgres psql
\password postgres
create database mancalajedb
\q
```

## Setting up Virtual Machines 💻

### Setting up OpenShift

[![alt text](Documentation/mje-openshift-s.png)](https://manage.openshift.com/)

-   Open an account
	-   [Openshift online](https://manage.openshift.com/)
-   Setup OKD (Original Community Distribution of Kubernetes)
	-   [OKD](https://www.okd.io/index.html)
-   Install Minishift

```bash
brew cask install minishift
brew cask install --force minishift
minishift addons install --defaults
minishift addons enable admin-user
minishift start --vm-driver=virtualbox
brew install openshift-cli
oc adm policy --as system:admin add-cluster-role-to-user cluster-admin developer
minishift console
oc create rolebinding default-view --clusterrole=view --serviceaccount=mancalaje:default --namespace=mancalaje
```

-   Install for systems running Ubuntu on a Windows machine

```bash
apt install virtualbox-dkms
```

```bash
Error starting the VM: Error creating the VM. Error with pre-create check: "This computer doesn't have VT-X/AMD-v enabled. Enabling it in the BIOS is mandatory"
```

## Setting up minikube 💻

[![alt text](Documentation/mje-docker-s.png "Docker")](https://www.docker.com/)
[![alt text](Documentation/mje-kubernetes-s.png "Kubernetes")](https://kubernetes.io/)
[![alt text](Documentation/mje-minikube-s.png "Minikube")](https://github.com/kubernetes/minikube)
[![alt text](Documentation/mje-vmware-s.png "VMWare")](https://www.vmware.com/)
[![alt text](Documentation/mje-virtualbox-s.png "Virtual Box")](https://www.virtualbox.org/)

NOTE: If you want to run this with vmware please install [VMWare Fusion](https://www.vmware.com/products/fusion/fusion-evaluation.html). You will need this to use the vmrun command. 📝

### Configure minikube ⌨️

```bash
minikube delete # Just in case 😉
minikube config set vm-driver virtualbox
minikube start --vm-driver=virtualbox --extra-config=apiserver.service-node-port-range=1-30000
minikube addons enable ingress # Not mandatory for now 🙂
kubectl config use-context minikube
minikube mount .:/mancalaje
minikube ssh
cd /mancalaje/docker-psql
docker build --file=Dockerfile --tag=mancalaje-postgresql:latest --rm=true .
cd /mancalaje/mancalaje-service
docker build --file=Dockerfile --tag=mancalaje:latest --rm=true .
cd /mancalaje/mancalaje-fe/docker-files
docker build --file=Dockerfile --tag=mancalaje-fe:latest --rm=true .
exit
```

### Configure deployment ⌨️

```bash
kubectl create -f docker-psql/postgres-deployment.yaml
kubectl create -f mancalaje-service/mancalaje-deployment.yaml
kubectl create -f mancalaje-fe/mancalaje-fe-deployment.yaml

kubectl delete service mancalaje-postgresql
kubectl delete deployment mancalaje-postgresql

kubectl delete service mancalaje
kubectl delete deployment mancalaje

kubectl delete service mancalaje-fe
kubectl delete deployment mancalaje-fe

minikube service mancalaje-postgresql
minikube service mancalaje
minikube service mancalaje-fe

kubectl get deployments
kubectl get services
kubectl get pods
```

🚀 and we are redy to go!

### Minikube Hints & Tricks

-   Install minikube for MAC-OS

```bash
brew install minikube
brew link kubernetes-cli
brew link --overwrite kubernetes-cli
brew install docker-machine-driver-virtualbox
brew link --overwrite --dry-run docker-machine
minikube config set driver virtualbox
```

-   Install minikube for linux (not fully tested)

```bash
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 \
  && chmod +x minikube
mkdir -p /usr/local/bin
install minikube /usr/local/bin/
```

-   Install minikube for Windows (not fully tested)
	-   Please install:
		-   [VMWare Workstation Player](https://www.vmware.com/products/workstation-player.html)
		-   [VMWare Workstation](https://www.vmware.com/products/workstation-pro/workstation-pro-evaluation.html)
		-   [VMware VIX 1.15.7](https://my.vmware.com/web/vmware/details?productId=640&downloadGroup=PLAYER-1253-VIX1157) (This may be optional. Check your VMWare folder for the presence of vmrun.exe)
	-   Add this to your $PATH: C:\Program Files (x86)\VMware\VMware VIX

```bash
Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Hyper-V -All
choco install -y docker-machine-vmware -pre
```

-   VMWare runs

```bash
bcdedit /set hypervisorlaunchtype off
minikube start --alsologtostderr -v=7 --vm-driver vmware
minikube start --vm-driver=vmware
```

-   Use and mount minikube

```bash
minikube config set vm-driver vmware
minikube start
kubectl config use-context minikube
minikube mount .:/mancalaje
minikube ssh
cd /mancalaje/mancalaje-service
docker build --file=Dockerfile --tag=mancalaje:latest --rm=true .
```

-   Running jar with minikube (not fully tested)

```bash
kubectl config use-context minikube
kubectl cluster-info
kubectl run demo-backend --image=demo-backend:latest --port=8080 --image-pull-policy Never
```

-   Remove minikube

```bash
minikube stop
minikube delete
rm -rf ~/.minikube
rm -rf ~/.kube
brew uninstall minikube
```

## Hints & Tricks

-   Install java versions without [SDKMan](https://sdkman.io/) for [ubuntu prompt for windows](https://www.microsoft.com/en-us/p/ubuntu/9nblggh4msv6?activetab=pivot:overviewtab).

```bash
apt-get -y update
apt-get -y upgrade
apt -y install apt-transport-https ca-certificates wget dirmngr gnupg software-properties-common
wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | apt-key add -
add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/
apt -y update
sudo apt -y install openjdk-11-jdk
sudo apt install openjdk-13-jdk
sudo apt -y install adoptopenjdk-8-hotspot
sudo apt -y autoremove
```

- .bashrc file to get Gradle, GitPrompt, [SDKMAN](https://sdkman.io/) and some handy aliases in a Windows environment with [MinGW](http://www.mingw.org/).

```bash
export GRADLE_HOME=/opt/gradle/gradle-6.1.1
export PATH=${GRADLE_HOME}/bin:${PATH}
alias ll='ls -l -a --color=auto'
if [ -f "/root/.bash-git-prompt/gitprompt.sh" ]; then
    GIT_PROMPT_ONLY_IN_REPO=1
    source /root/.bash-git-prompt/gitprompt.sh
fi

alias java13="sdk use java 13.0.2.hs-adpt"
alias java12="sdk use java 12.0.2.hs-adpt"
alias java8="sdk use java 8.0.242.hs-adpt"
alias m2disable="rm ~/.m2/settings.xml"
alias m2enable="cp /your_repo_folder/settings.xml ~/.m2/"

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/root/.sdkman"
[[ -s "/root/.sdkman/bin/sdkman-init.sh" ]] && source "/root/.sdkman/bin/sdkman-init.sh"
```

- .bashrc file to get Gradle, GitPrompt and some handy aliases in a Windows environment with [ubuntu prompt for windows](https://www.microsoft.com/en-us/p/ubuntu/9nblggh4msv6?activetab=pivot:overviewtab).

```bash
export GRADLE_HOME=/opt/gradle/gradle-6.1.1
export PATH=${GRADLE_HOME}/bin:${PATH}
alias ll='ls -l -a --color=auto'
if [ -f "/root/.bash-git-prompt/gitprompt.sh" ]; then
    GIT_PROMPT_ONLY_IN_REPO=1
    source /root/.bash-git-prompt/gitprompt.sh
fi

alias java8="export JAVA_HOME=/usr/lib/jvm/adoptopenjdk-8-hotspot-amd64 && update-java-alternatives -s adoptopenjdk-8-hotspot-amd64"
alias java11="export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64 && update-java-alternatives -s java-1.11.0-openjdk-amd64"
alias java12="echo \"Java 12 is not available. Setting up 13\" && export JAVA_HOME=/usr/lib/jvm/java-13-oracle && update-java-alternatives -s java-13-oracle"
alias java13="export JAVA_HOME=/usr/lib/jvm/java-13-oracle && update-java-alternatives -s java-13-oracle"

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/root/.sdkman"
[[ -s "/root/.sdkman/bin/sdkman-init.sh" ]] && source "/root/.sdkman/bin/sdkman-init.sh"
```

-   Update Alternatives

```bash
 update-java-alternatives -l
```

-   Fix Apt

```bash
dpkg --configure -a
```

-   Git tag change

```bash
git tag new-tag old-tag
git tag -d old-tag
git push origin :refs/tags/old-tag
git push --tags
git pull --prune --tags
```
