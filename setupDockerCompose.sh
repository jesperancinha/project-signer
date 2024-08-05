#!/usr/bin/env sh
if [ ! -f /usr/local/bin/docker-compose ]; then
  curl -O https://raw.githubusercontent.com/jesperancinha/project-signer/master/docker-compose
  sudo cp docker-compose /usr/local/bin/docker-compose;
else
  echo "File already exists! Continuing"
fi
