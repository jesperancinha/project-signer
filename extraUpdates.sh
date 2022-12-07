#!/usr/bin/env bash

cypress=$(cat e2e/package.json | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev)
echo -e "Cypress version is \e[32m$cypress\e[0m"

# TODO