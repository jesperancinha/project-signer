#!/usr/bin/env sh
echo "Starting upgrade from docker-compose version $(docker-compose -v | tail -n 1)"; \
echo "If DOCKER_COMPOSE_VERSION env variable is not given, this upgrade will install the latest version"; \
if [ -z "$DOCKER_COMPOSE_VERSION" ]; then \
    DOCKER_COMPOSE_VERSION=$(curl -s https://api.github.com/repos/docker/compose/releases/latest | grep 'tag_name' | cut -d\" -f4); \
else \
    echo "DOCKER_COMPOSE_VERSION not given. Upgrading to latest available version..."; \
fi; \
echo "Installing version $DOCKER_COMPOSE_VERSION of docker-compose"; \
echo ""; \
DOCKER_CONFIG=${DOCKER_CONFIG:-$HOME/.docker}; \
mkdir -p $DOCKER_CONFIG/cli-plugins; \
curl -SL https://github.com/docker/compose/releases/download/$DOCKER_COMPOSE_VERSION/docker-compose-linux-x86_64 -o $DOCKER_CONFIG/cli-plugins/docker-compose; \
chmod +x $DOCKER_CONFIG/cli-plugins/docker-compose;
if [ ! -f /usr/bin/docker-compose ]; then
  sudo ln -s ~/.docker/cli-plugins/docker-compose /usr/bin/docker-compose
else
  echo "File already exists! Continuing"
fi
