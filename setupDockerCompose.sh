#!/usr/bin/env sh
if [ ! -f /usr/local/bin/docker-compose ]; then
sudo cp docker-compose /usr/local/bin/docker-compose;
else
  echo "File already exists! Continuing"
fi
