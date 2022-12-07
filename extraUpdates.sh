#!/usr/bin/env bash

cypress=$(cat < "e2e/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev)
oldcheckout01="actions/checkout@v1"
oldcheckout02="actions/checkout@v2"
newcheckout="actions/checkout@v3"
oldsetupjava01="actions/setup-java@v1"
oldsetupjava02="actions/setup-java@v2"
newsetupjava="actions/setup-java@v3"
echo -e "Cypress Version"
echo -e "--- New version is \e[32m$cypress\e[0m"
echo -e "GitHub Workflow Updates"
echo -e "--- Update Checkout from \e[32m$oldcheckout01\e[0m to \e[32m$newcheckout\e[0m"
echo -e "--- Update Checkout from \e[32m$oldcheckout02\e[0m to \e[32m$newcheckout\e[0m"
echo -e "--- Update Setup Java from \e[32m$oldsetupjava01\e[0m to \e[32m$newsetupjava\e[0m"
echo -e "--- Update Setup Java from \e[32m$oldsetupjava02\e[0m to \e[32m$newsetupjava\e[0m"

# TODO