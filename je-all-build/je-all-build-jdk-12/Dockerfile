FROM jesperancinha/je-all-build:0.0.2

RUN update-alternatives --remove java /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN wget --no-cookies --no-check-certificate https://download.java.net/openjdk/jdk12/ri/openjdk-12+32_linux-x64_bin.tar.gz

RUN tar -xvzf openjdk-12+32_linux-x64_bin.tar.gz -C /opt

RUN update-alternatives --install /usr/bin/java java /opt/jdk-12/bin/java 1

RUN update-alternatives --install /usr/bin/javac javac /opt/jdk-12/bin/javac 1

