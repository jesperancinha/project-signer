FROM library/postgres

RUN apt -y update

RUN apt-get -y install apt-utils

RUN apt -y install python3-pip

RUN apt -y install curl

RUN apt -y install rpm

RUN apt -y install wget

RUN apt-get -y install cron

RUN apt-get -y install vim

RUN apt-get -y install maven

RUN curl -sL https://deb.nodesource.com/setup_13.x | bash -

RUN apt-get install -y nodejs

RUN pip3 install flask

RUN mkdir -p /usr/share/man/man1

RUN npm install -g grunt-cli

RUN update-alternatives --remove java /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/otn-pub/java/jdk/13.0.2+8/d4173c853231432d94f001e99d882ca7/jdk-13.0.2_linux-x64_bin.deb

RUN apt -y install ./jdk-13.0.2_linux-x64_bin.deb

RUN update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-13.0.2/bin/java 1

ENV runningFolder /usr/local/bin/

WORKDIR ${runningFolder}
