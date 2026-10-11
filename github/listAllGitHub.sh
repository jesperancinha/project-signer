#!/usr/bin/env bash

echo -e "\e[32Listing all git repos from user $1\e[0m"
echo -e "\e[32mExample usage: ./listAllGitHub.sh jesperancinha 100 jesperancinhaOrg 100\e[0m"
user=$1
pages=$2
org=$3
porg=$4
repos=$(curl -s https://api.github.com/users/"${user}"/repos?per_page="${pages}")

cd ../..

for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
  _jq() {
    echo "${row}" | base64 --decode | jq -r "${1}"
  }
  if [[ $(_jq '.fork') =~ ^'false'$ ]]; then
    repoAddress=$(_jq '.ssh_url')
    echo "git clone ""${repoAddress}"
  fi
done
repos=$(curl -s https://api.github.com/orgs/"${org}"/repos?per_page="${porg}")
for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
  _jq2() {
    echo "${row}" | base64 --decode | jq -r "${1}"
  }
  if [[ $(_jq2 '.fork') =~ ^'false'$ ]]; then
    repoAddress=$(_jq2 '.ssh_url')
    echo "git clone ""${repoAddress}"
  fi
done

cd project-signer/github || exit
