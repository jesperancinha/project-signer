FROM jesperancinha/je-all-build:0.0.1

RUN update-alternatives --remove java /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN rm -rf /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN wget --no-check-certificate -c --header  "Cookie: oraclelicense=accept-securebackup-cookie" "https://download.oracle.com/otn-pub/java/jdk/14+36/076bab302c7b4508975440c56f6cc26a/jdk-14_linux-x64_bin.deb"

RUN apt install ./jdk-14_linux-x64_bin.deb

RUN update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-14/bin/java 1
