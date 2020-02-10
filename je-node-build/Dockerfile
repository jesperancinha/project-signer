FROM nginx

RUN apt -y update

RUN apt -y install curl

RUN apt -y install wget

RUN apt-get -y install cron

RUN apt-get -y install vim

RUN apt-get -y install zip unzip

RUN apt-get -y install git

RUN apt-get -y install git-gui

RUN mkdir -p /usr/share/man/man1

RUN curl -sL https://deb.nodesource.com/setup_13.x | bash -

RUN apt-get install -y nodejs

RUN npm install -g grunt-cli

RUN npm install -g pm2

RUN npm install -g tsc

RUN npm install -g yarn

RUN npm install -g bower

RUN curl --compressed -o- -L https://yarnpkg.com/install.sh | bash

RUN cd ~ && git clone https://github.com/magicmonty/bash-git-prompt.git ~/.bash-git-prompt --depth=1

RUN echo "alias ll='ls -l -a --color=auto'" | tee -a ~/.bashrc /etc/profile

RUN echo "if [ -f \"$HOME/.bash-git-prompt/gitprompt.sh\" ]; then" | tee -a ~/.bashrc /etc/profile

RUN echo "    GIT_PROMPT_ONLY_IN_REPO=1" | tee -a ~/.bashrc /etc/profile

RUN echo "    source $HOME/.bash-git-prompt/gitprompt.sh" | tee -a ~/.bashrc /etc/profile

RUN echo "fi" | tee -a ~/.bashrc /etc/profile