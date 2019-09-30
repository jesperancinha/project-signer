#!/usr/bin/env bash

echo -e '\e[31m'WARNING!!'\e[0m'
echo -e '\e[32m'Carefull when using this script. It will clone EVERY repo from user $1 into the root directory!'\e[0m'
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]
then
    user=$1
    pages=$2
    repos=$(curl https://api.github.com/users/${user}/repos?per_page=${pages})
    cd ..
    for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
        _jq() {
            echo ${row} | base64 --decode | jq -r ${1}
        }
        if [[ $(_jq '.fork') =~ ^'false'$ ]]
        then
            repoAddress=$(_jq '.ssh_url')
            echo ${repoAddress}
            git clone ${repoAddress}
        fi
    done
    cd project-signer
fi