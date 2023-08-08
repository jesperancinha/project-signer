FROM jesperancinha/je-all-build:0.0.2

RUN update-alternatives --remove java /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN apt-get -y update

RUN apt-get -y upgrade

RUN apt -y update

RUN apt -y install apt-transport-https ca-certificates wget dirmngr gnupg software-properties-common

RUN wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | apt-key add -

RUN add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/

RUN apt -y update

RUN apt -y install adoptopenjdk-8-hotspot
