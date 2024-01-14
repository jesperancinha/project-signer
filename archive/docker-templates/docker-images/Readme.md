# Docker images

## Introduction

>Hi there, this directory and its contents contain image definitions and specifications I created in the past to help me
>create docker environments.
>However, I ended up not needing this images and for a long time I have already found ways to use existing docker-images
>to serve multiple requirements.
>This whole directory is no longer in use, but feel free to have a look at it.

In this repo you will find docker images for many purposes with extended functionality.

## Docker notes

```bash
# If exists
docker-machine rm dev 

docker-machine create --driver virtualbox dev

docker-machine env dev

eval $(docker-machine env dev)

# If not started
docker-machine start dev

docker build .

docker-machine ssh dev

docker ps -a

#Container access
docker exec -it <container ID> /bin/bash

docker run -p 80:80 "<image ID>"

docker run -d -v /webroot:/var/www/html -p 80:80 --name "<name of container>" "<image ID>"

docker run -it ubuntu sh

docker run ubuntu

docker-machine ip dev

docker build . -t "<image ID>"

docker run "<container ID>" -d -p 8080:80 -p 5000:5000 "<image ID>"

docker system prune -a

docker builder prune
```

## Tools & Tips

### Git tagging

```bash
git tag new-tag old-tag
git tag -d old-tag
git push origin :refs/tags/old-tag
git push --tags
git pull --prune --tags
```

## References

-   [Use Bash Strict Mode (Unless You Love Debugging)](http://redsymbol.net/articles/unofficial-bash-strict-mode/)
-   [Linux containers](https://linuxcontainers.org/)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
