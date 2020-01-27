FROM nginx

RUN apt -y update

RUN apt -y install python3-pip

RUN pip3 install flask

RUN apt -y install curl

RUN apt -y install wget

RUN apt-get -y install cron

RUN mkdir -p /usr/share/man/man1

RUN curl -sL https://deb.nodesource.com/setup_13.x | bash -

RUN apt-get install -y nodejs

RUN npm install -g grunt-cli

RUN npm install -g pm2

RUN npm install -g tsc

RUN npm install -g yarn

RUN rm /bin/sh && ln -s /bin/bash /bin/sh

RUN curl -s "https://get.sdkman.io" | bash

RUN . "$HOME/.sdkman/bin/sdkman-init.sh"

RUN su - root -c "sdk install gradle 6.1.1"

RUN su - root -c "sdk install java 11.0.6.hs-adpt"

RUN su - root -c "sdk install java 12.0.2.hs-adpt"

RUN su - root -c "sdk install java 13.0.1.hs-adpt"
