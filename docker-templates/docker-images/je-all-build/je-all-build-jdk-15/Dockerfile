FROM jesperancinha/je-all-build:0.0.3

RUN update-alternatives --remove java /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN rm -rf /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN wget --no-check-certificate -c "https://download.java.net/java/early_access/jdk15/27/GPL/openjdk-15-ea+27_linux-x64_bin.tar.gz"

RUN tar -xvzf openjdk-15-ea+27_linux-x64_bin.tar.gz

RUN mv jdk-15 /usr/lib/jvm

RUN update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-15/bin/java 1

RUN rm openjdk-15-ea+27_linux-x64_bin.tar.gz

RUN wget --no-check-certificate -c "https://services.gradle.org/distributions/gradle-6.5-bin.zip"

RUN unzip -d /opt/gradle gradle-6.5-bin.zip

RUN rm gradle-6.5-bin.zip

RUN rm -r /opt/gradle/gradle-6.1.1

ENV PATH="/opt/gradle/gradle-6.5/bin:${PATH}"

RUN echo "export PATH=$PATH" > /etc/environment

ENTRYPOINT ["tail", "-f", "/dev/null"]