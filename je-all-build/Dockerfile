FROM nginx

RUN apt -y update

RUN apt -y install python3-pip

RUN pip3 install flask

RUN apt -y install curl

RUN apt -y install rpm

RUN apt -y install wget

RUN apt-get -y install cron

RUN apt-get -y install vim

RUN apt-get -y install zip unzip

RUN apt-get -y install postgresql-client

RUN apt-get -y install maven

RUN ["/bin/bash", "-c", "debconf-set-selections <<< \"postfix postfix/mailname string test\""]

RUN ["/bin/bash", "-c", "debconf-set-selections <<< \"postfix postfix/main_mailer_type string 'No configuration'\""]

RUN apt-get install -y --assume-yes postfix

RUN mkdir -p /usr/share/man/man1

RUN curl -sL https://deb.nodesource.com/setup_13.x | bash -

RUN apt-get install -y nodejs

RUN npm install -g grunt-cli

RUN npm install -g pm2

RUN npm install -g tsc

RUN npm install -g yarn

RUN wget https://services.gradle.org/distributions/gradle-6.1.1-bin.zip -P /tmp

RUN unzip -d /opt/gradle /tmp/gradle-*.zip

RUN echo 'export GRADLE_HOME=/opt/gradle/gradle-6.1.1' | tee -a ~/.bashrc /etc/profile

RUN echo 'export PATH=${GRADLE_HOME}/bin:${PATH}' | tee -a ~/.bashrc /etc/profile

ENV GRADLE_HOME /opt/gradle/gradle-6.1.1

ENV PATH $PATH:/opt/gradle/gradle-6.1.1/bin

RUN wget www.scala-lang.org/files/archive/scala-2.13.0.deb

RUN dpkg -i scala*.deb

RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list

RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823

RUN apt-get update

RUN apt-get install sbt -y

RUN curl --compressed -o- -L https://yarnpkg.com/install.sh | bash

RUN cd ~ && git clone https://github.com/magicmonty/bash-git-prompt.git ~/.bash-git-prompt --depth=1

RUN echo "alias ll='ls -l -a --color=auto'" | tee -a ~/.bashrc /etc/profile

RUN echo "if [ -f \"$HOME/.bash-git-prompt/gitprompt.sh\" ]; then" | tee -a ~/.bashrc /etc/profile

RUN echo "    GIT_PROMPT_ONLY_IN_REPO=1" | tee -a ~/.bashrc /etc/profile

RUN echo "    source $HOME/.bash-git-prompt/gitprompt.sh" | tee -a ~/.bashrc /etc/profile

RUN echo "fi" | tee -a ~/.bashrc /etc/profile