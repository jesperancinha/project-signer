FROM cassandra:3.11.6

ENV LANG=C.UTF-8

WORKDIR /root

RUN apt -y update
RUN apt -y upgrade

RUN apt -y install gnupg2
RUN apt -y install wget

RUN wget -O- https://packages.erlang-solutions.com/ubuntu/erlang_solutions.asc | apt-key add -
RUN echo "deb https://packages.erlang-solutions.com/ubuntu bionic contrib" | tee /etc/apt/sources.list.d/rabbitmq.list
RUN apt -y update
RUN apt -y install erlang

RUN wget -O- https://dl.bintray.com/rabbitmq/Keys/rabbitmq-release-signing-key.asc | apt-key add -
RUN wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | apt-key add -
RUN apt-get -y update
RUN apt-get -y install lsb-release
RUN echo "deb https://dl.bintray.com/rabbitmq/debian $(lsb_release -sc) main" | tee /etc/apt/sources.list.d/rabbitmq.list
RUN apt -y update
RUN apt -y install rabbitmq-server

RUN apt -y install nodejs
RUN apt -y install python3

RUN apt-get -y download python3-distutils
RUN dpkg-deb -x python3-distutils_3.6.9-1~18.04_all.deb /
RUN curl https://bootstrap.pypa.io/get-pip.py | python3.6
RUN rm *.deb

RUN apt -y install mosquitto

RUN mkdir ~/Downloads
RUN curl "https://downloads.apache.org/kafka/2.5.0/kafka_2.13-2.5.0.tgz" -o ~/Downloads/kafka.tgz
RUN mkdir ~/kafka && cd ~/kafka
RUN tar -xvzf ~/Downloads/kafka.tgz -C /usr/local/etc
RUN mv /usr/local/etc/kafka_2.13-2.5.0 /usr/local/etc/kafka
RUN rm -r ~/Downloads
RUN rm -r ~/kafka

COPY startKafka.sh /root
COPY startZookeeper.sh /root

COPY cassandra-env.sh /opt/cassandra/conf/cassandra-env.sh

EXPOSE 9042 9160

#ENTRYPOINT ["tail", "-f", "/dev/null"]
