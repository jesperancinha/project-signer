FROM centos:centos8

ENV LANG=C.UTF-8

WORKDIR /root

RUN yum clean all

RUN yum -y install epel-release

RUN yum -y install rsync

RUN yum -y upgrade

RUN yum -y update

RUN yum -y install -y gcc-c++ make

RUN curl -sL https://rpm.nodesource.com/setup_13.x | bash -

RUN yum -y install nodejs

RUN yum -y install python3

RUN curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py"

RUN python3 get-pip.py

RUN rm get-pip.py

RUN yum -y install wget

RUN curl -s https://packagecloud.io/install/repositories/rabbitmq/rabbitmq-server/script.rpm.sh | bash

RUN yum makecache -y --disablerepo='*' --enablerepo='rabbitmq_rabbitmq-server'

RUN yum -y install rabbitmq-server

RUN mkdir ~/Downloads

RUN curl "https://downloads.apache.org/kafka/2.5.0/kafka_2.13-2.5.0.tgz" -o ~/Downloads/kafka.tgz

RUN mkdir ~/kafka && cd ~/kafka

RUN tar -xvzf ~/Downloads/kafka.tgz --strip 1

COPY zookeeper.service /etc/systemd/system/zookeeper.service

COPY kafka.service /etc/systemd/system/kafka.service

RUN wget http://rpms.remirepo.net/enterprise/8/remi/x86_64/mosquitto-1.5.4-1.el8.remi.x86_64.rpm

RUN wget http://rpms.remirepo.net/enterprise/8/remi/x86_64/libwebsockets-2.4.2-1.el8.remi.x86_64.rpm

RUN rpm -Uvh libwebsockets-2.4.2-1.el8.remi.x86_64.rpm

RUN rpm -Uvh mosquitto-1.5.4-1.el8.remi.x86_64.rpm

COPY cassandra.repo /etc/yum.repos.d/

RUN dnf -y install dsc20

COPY cassandra.service /etc/systemd/system/

ENTRYPOINT ["tail", "-f", "/dev/null"]
