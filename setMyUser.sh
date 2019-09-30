#!/usr/bin/env bash

echo -e '\e[31m'WARNING!!'\e[0m'
echo -e '\e[32m'Careful when using this script. It is ONLY meant to be used as a tool \for  setting up the local user'\e[0m'
echo -e '\e[32m'Setting up user $1 with email $2'\e[0m'
echo -e '\e[32m'I\'ll make it re-usable in the future'\e[0m'
read -p "Are you sure you want to do this? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]
then
    username=$1
    email=$2
    cd ..
    for item in *; do
        cd "$item"
        echo "----------------- Setting up user $username with email $email on repo $item -----------------"
        git config --replace-all --local user.name "$username"
        git config --replace-all --local user.email "$email"
        cd ..
    done
    cd project-signer
fi