FROM jesperancinha/je-all-build:0.0.1

RUN update-alternatives --remove java /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN rm -rf /usr/lib/jvm/java-11-openjdk-amd64/bin/java

RUN wget --no-check-certificate -c "https://download.java.net/java/GA/jdk14.0.1/664493ef4a6946b186ff29eb326336a2/7/GPL/openjdk-14.0.1_linux-x64_bin.tar.gz"

RUN tar -xvzf openjdk-14.0.1_linux-x64_bin.tar.gz

RUN mv jdk-14.0.1 /usr/lib/jvm/jdk-14

RUN update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-14/bin/java 1

RUN rm openjdk-14.0.1_linux-x64_bin.tar.gz

RUN wget --no-check-certificate -c "https://services.gradle.org/distributions/gradle-6.5-bin.zip"

RUN unzip -d /opt/gradle gradle-6.5-bin.zip

RUN rm gradle-6.5-bin.zip

RUN rm -r /opt/gradle/gradle-6.1.1

ENV PATH="/opt/gradle/gradle-6.5/bin:${PATH}"

RUN echo "export PATH=$PATH" > /etc/environment

ENTRYPOINT ["tail", "-f", "/dev/null"]