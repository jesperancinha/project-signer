#!/usr/bin/env sh

echo -e '\033[92mChanging to the first available SDK MAN Java SDK 20 version.\033[0m'
echo -e '\033[91mRemember to run this command like this: . ./sdk20.sh\033[0m'
sdk install java 20.0.1-open
sdk use java $(sdk list java | grep installed | grep "| 20" |  cut -d'|' -f6- | cut -d' ' -f2-)
