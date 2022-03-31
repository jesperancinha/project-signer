b: build
build:
	mvn clean install
test:
	mvn test
local:
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
release:
	export GPG_TTY=$(tty)
	mvn clean deploy -Prelease
	mvn nexus-staging:release  -Prelease
docker-delete:
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker stop {}
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker rm {}
prune-all: docker-delete
	docker network prune
	docker system prune --all
	docker builder prune
	docker system prune --all --volumes
