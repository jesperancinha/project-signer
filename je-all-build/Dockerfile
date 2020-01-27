FROM nginx

RUN apt -y update

RUN apt -y install python3-pip

RUN pip3 install flask

RUN apt -y install curl

RUN apt -y install rpm

RUN apt -y install wget

RUN apt-get -y install cron

RUN apt-get -y install vim

RUN apt-get install zip unzip

RUN mkdir -p /usr/share/man/man1

RUN apt-get -y install maven

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
