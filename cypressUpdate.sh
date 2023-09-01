#!/usr/bin/env bash

cypress=$(cat < "e2e/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev)
cypress_docker=$(cat < "e2e/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | cut -c 2- )
echo -e "Cypress Version"
echo -e "--- New version is \e[32m$cypress\e[0m"
echo -e "--- New docker version is \e[32m$cypress_docker\e[0m"
sed -E 's/"cypress": .*/"cypress": "'"$cypress"'"/g' e2e/package.json

read -p "Are you sure? (Yy/Nn)" -n 1 -r

if [[ $REPLY =~ ^[Yy]$ ]]; then
  cd e2e || exit
  sudo npm install -g npm-check-updates@latest
  ncu -u
  cd ../..
  for item in *; do
    if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
      if [[ ! $item = "project-signer" ]]; then
        cd "${item}" || exit
        if [[ -f e2e/package.json ]]; then
          echo "----------------- Updating Cypress In ${item} -----------------"
          echo "Cypress from $(cat < "e2e/package.json" | grep -v cypress-pipe | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev)"
          sed -E 's/"cypress": "1.*"/"cypress": "'"$cypress_docker"'/g' "e2e/package.json" > e2e/packagetmp.json
          sed -E 's/"cypress": "^.*"/"cypress": "'"$cypress_docker"'/g' "e2e/packagetmp.json" > e2e/package.json
          sed -E 's/"cypress": ".*"/"cypress": "'"$cypress_docker"'/g' "e2e/packagetmp.json" > e2e/package.json
          sed -E 's/image: "cypress\/included:.*"/image: "cypress\/included:'"$cypress_docker"'/g' "e2e/docker-compose.yml" > e2e/docker-composetmp.yml
          rm e2e/packagetmp.json
          mv e2e/docker-composetmp.yml e2e/docker-compose.yml
        fi
        cd ..
      fi
    fi
  done
  cd project-signer || exit
fi
