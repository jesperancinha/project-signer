SHELL := /bin/bash
GRADLE_VERSION ?= 8.2

b: build
build:
	mvn clean install
build-cypress:
	cd e2e; \
	yarn; \
	npm i
test:
	mvn test
local:
	mkdir -p bin
no-test:
	mvn clean install spring-boot:repackage -DskipTests
release:
	export GPG_TTY=$(tty)
	mvn clean deploy -Prelease
	mvn nexus-staging:release  -Prelease
docker-delete:
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker stop {}
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker rm {}
prune-all: docker-delete
	docker network prune -f
	docker system prune --all -f
	docker builder prune -f
	docker system prune --all --volumes -f
sign-all: no-test
	java -jar -Dspring.profiles.active=prod project-signer/target/project-signer.jar -l "project-signer-templates/licenses/APACHE2.template,project-signer-templates/licenses/ISC.template,project-signer-templates/licenses/MIT.template" -t "project-signer-templates/Readme.md" -d ../ -r project-signer-quality "License" "About me ?????????????????????????????????" "About me" "Achievements"
upgrade-gradle:
	sudo apt upgrade
	sudo apt update
	export SDKMAN_DIR="$(HOME)/.sdkman"; \
	[[ -s "$(HOME)/.sdkman/bin/sdkman-init.sh" ]]; \
	source "$(HOME)/.sdkman/bin/sdkman-init.sh"; \
	sdk update; \
	gradleOnlineVersion=$(shell curl -s https://services.gradle.org/versions/current | jq .version | xargs -I {} echo {}); \
	if [[ -z "$$gradleOnlineVersion" ]]; then \
		sdk install gradle $(GRADLE_VERSION); \
		sdk use gradle $(GRADLE_VERSION); \
	else \
		sdk install gradle $$gradleOnlineVersion; \
		sdk use gradle $$gradleOnlineVersion; \
		export GRADLE_VERSION=$$gradleOnlineVersion; \
	fi; \
	make upgrade
install-linux:
	sudo apt-get install jq
	sudo apt-get install curl
	curl https://services.gradle.org/versions/current
upgrade-cypress-all-projects:
	./cypressUpdate.sh