#!/usr/bin/env bash

user=$1
pages=$2
org=$3
porg=$4

user=${user:=jesperancinha}
pages=${pages:=100}
org=${org:=jesperancinhaOrg}
porg=${porg:=100}

echo -e '\e[31mWARNING!!\e[0m'
echo -e "\e[32mCarefull when using this script! It will push an extra commit to automated merge requests\e[0m"
echo -e "\e[32mExample usage: ./startMergeAll.sh $user $pages $org $porg\e[0m"
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  repos=$(curl https://api.github.com/users/"${user}"/repos?per_page="${pages}")
  cd ..
  for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
    _jq() {
      echo "${row}" | base64 --decode | jq -r "${1}"
    }
    if [[ $(_jq '.fork') =~ ^'false'$ ]]; then
      repoAddress=$(_jq '.name')
      echo "${repoAddress}"
      if [ -d "${repoAddress}" ]; then
        cd "${repoAddress}"
        curl -H "Accept: application/vnd.github.v3+json" "https://api.github.com/repos/jesperancinha/buy-odd-yucca-concert/pulls?state=open" | jq '.[] | .head.ref' | grep "update-" | xargs -I {} git checkout {} | make accept-prs
        # shellcheck disable=SC2103
        cd ..
      fi
    fi
    sleep 0.5
  done
  repos=$(curl https://api.github.com/orgs/"${org}"/repos?per_page="${porg}")
  for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
    _jq2() {
      echo "${row}" | base64 --decode | jq -r "${1}"
    }
    if [[ $(_jq2 '.fork') =~ ^'false'$ ]]; then
      repoAddress=$(_jq2 '.name')
      echo "${repoAddress}"
      if [ -d "${repoAddress}" ]; then
        cd "${repoAddress}"
        # shellcheck disable=SC2103
        cd ..
      fi
    fi
    sleep 0.5
  done
  cd project-signer || exit
fi
