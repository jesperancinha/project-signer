## Hints and tricks

### Generating Certificates

```bash
openssl req -new -newkey rsa:4096 -nodes -keyout yourfinance.key -out yourfinance.csr
openssl x509 -req -sha256 -days 365 -in yourfinance.csr -signkey yourfinance.key -out yourfinance.pem
```

### NodeJS Update NPM Update

```bash
npm audit fix
npm install npm@latest -g
npm update -g
npm install npm@latest -g
npm update -g
npm install -g npm-check-updates
ncu -u
npm update
npm update --legacy-peer-deps
yarn install
```

### Java installation via [SDKMAN!](https://sdkman.io/install)

-   Install java versions with [SDKMan](https://sdkman.io/) for MAC-OS and Linux based systems

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 8.0.242.hs-adpt
sdk install java 11.0.6.hs-adpt
sdk install java 12.0.2.hs-adpt
sdk install java 13.0.2.hs-adpt
sdk install java 14.0.0.hs-adpt
```

### Java installation via [APT](http://manpages.ubuntu.com/manpages/xenial/en/man8/apt.8.html)

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

### Aliases to switch version

#### With [MinGW](http://www.mingw.org/)

- .bashrc file to get GitPrompt, [SDKMAN](https://sdkman.io/) and some handy aliases in a Windows environment with [MinGW](http://www.mingw.org/) or just in a MAC-OS environment.

```bash
if [ -f "/root/.bash-git-prompt/gitprompt.sh" ]; then
    GIT_PROMPT_ONLY_IN_REPO=1
    source /root/.bash-git-prompt/gitprompt.sh
fi

alias java8="sdk use java 8.0.242.hs-adpt"
alias java11="sdk use java  11.0.6.hs-adpt"
alias java12="sdk use java 12.0.2.hs-adpt"
alias java13="sdk use java 13.0.2.hs-adpt"
alias java14="sdk use java 14.0.0.hs-adpt"
alias m2disable="rm ~/.m2/settings.xml"
alias m2enable="cp /your_repo_folder/settings.xml ~/.m2/"

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/root/.sdkman"
[[ -s "/root/.sdkman/bin/sdkman-init.sh" ]] && source "/root/.sdkman/bin/sdkman-init.sh"
```

#### With [ubuntu prompt for windows](https://www.microsoft.com/en-us/p/ubuntu/9nblggh4msv6?activetab=pivot:overviewtab)

- .bashrc file to get GitPrompt and some handy aliases in a Windows environment with [ubuntu prompt for windows](https://www.microsoft.com/en-us/p/ubuntu/9nblggh4msv6?activetab=pivot:overviewtab).

```bash
if [ -f "/root/.bash-git-prompt/gitprompt.sh" ]; then
    GIT_PROMPT_ONLY_IN_REPO=1
    source /root/.bash-git-prompt/gitprompt.sh
fi

alias java8="export JAVA_HOME=/usr/lib/jvm/adoptopenjdk-8-hotspot-amd64 && update-java-alternatives -s adoptopenjdk-8-hotspot-amd64"
alias java11="export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64 && update-java-alternatives -s java-1.11.0-openjdk-amd64"
alias java12="echo \"Java 12 is not available. Setting up 13\" && export JAVA_HOME=/usr/lib/jvm/java-13-oracle && update-java-alternatives -s java-13-oracle"
alias java13="export JAVA_HOME=/usr/lib/jvm/java-13-oracle && update-java-alternatives -s java-13-oracle"
alias m2disable="rm ~/.m2/settings.xml"
alias m2enable="cp /your_repo_folder/settings.xml ~/.m2/"
```

### Git tag change

```bash
git tag new-tag old-tag
git tag -d old-tag
git push origin :refs/tags/old-tag
git push --tags
git pull --prune --tags
```

### Setting up OpenShift [![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/openshift-50.png)](https://manage.openshift.com/)

-   Open an account
	
	-   [Openshift online](https://manage.openshift.com/)

-   Setup OKD (Original Community Distribution of Kubernetes)
	
	-   [OKD](https://www.okd.io/index.html)

## Install Minishift

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

### Git tag datetime listing

```bash
git for-each-ref --format="%(refname:short) | %(creatordate)" "refs/tags/*"
```

### [pbcopy](http://sweetme.at/2013/11/17/copy-to-and-paste-from-the-clipboard-on-the-mac-osx-command-line/)

```bash
curl -L "http://coolsite.com" | pbcopy
```


## Upgrade [YARN](https://yarnpkg.com/)

```bash
curl --compressed -o- -L https://yarnpkg.com/install.sh | bash
```

## Remove Docker-machine

NOTE: This process will remove old docker-machine installations.
User [Docker-Desktop](https://www.docker.com/products/docker-desktop) instead.

```bash
brew uninstall docker-machine-driver-vmware
brew uninstall --force docker-machine
docker system prune -a
```

### Git History Email Change

```bash
git filter-branch --env-filter '
WRONG_EMAIL="<old-mail>"
NEW_NAME="Joao Esperancinha"
NEW_EMAIL="<new-mail>

if [ "$GIT_COMMITTER_EMAIL" = "$WRONG_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$NEW_NAME"
    export GIT_COMMITTER_EMAIL="$NEW_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$WRONG_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$NEW_NAME"
    export GIT_AUTHOR_EMAIL="$NEW_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags
```

### Maven

```bash
mvn io.spring.javaformat:spring-javaformat-maven-plugin:0.0.27:apply
```

## References

- [How can I change the author name / email of a commit?](https://www.git-tower.com/learn/git/faq/change-author-name-email)
- [Get the time and date of git tags](https://stackoverflow.com/questions/13208734/get-the-time-and-date-of-git-tags/13208830)