#!/usr/bin/env bash

cypress=$(cat < "e2e/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev)
cypress_docker=$(cat < "e2e/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | cut -c 2- )
oldcheckout01="actions\/checkout@v1"
oldcheckout02="actions\/checkout@v2"
newcheckout="actions\/checkout@v3"
oldsetupjava01="actions\/setup-java@v1"
oldsetupjava02="actions\/setup-java@v2"
newsetupjava="actions\/setup-java@v3"
oldsetupnode01="actions\/setup-node@v1"
oldsetupnode02="actions\/setup-node@v2"
newsetupnode="actions\/setup-node@v3"
echo -e "Cypress Version"
echo -e "--- New version is \e[32m$cypress\e[0m"
echo -e "--- New docker version is \e[32m$cypress_docker\e[0m"
echo -e "GitHub Workflow Updates"
echo -e "--- Update Checkout from \e[32m$oldcheckout01\e[0m to \e[32m$newcheckout\e[0m"
echo -e "--- Update Checkout from \e[32m$oldcheckout02\e[0m to \e[32m$newcheckout\e[0m"
echo -e "--- Update Setup Java from \e[32m$oldsetupjava01\e[0m to \e[32m$newsetupjava\e[0m"
echo -e "--- Update Setup Java from \e[32m$oldsetupjava02\e[0m to \e[32m$newsetupjava\e[0m"
echo -e "--- Update Setup Node from \e[32m$oldsetupnode01\e[0m to \e[32m$newsetupnode\e[0m"
echo -e "--- Update Setup Node from \e[32m$oldsetupnode02\e[0m to \e[32m$newsetupnode\e[0m"

sed -E 's/"cypress": .*/"cypress": "'"$cypress"'"/g' e2e/package.json

REPLY='Y'

if [[ $REPLY =~ ^[Yy]$ ]]; then
  cd ..
  for item in *; do
    if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
      cd "${item}" || exit
      if [[ -f e2e/package.json ]]; then
        echo "----------------- Updating Cypress In ${item} -----------------"
        echo "Cypress from $(cat < "e2e/package.json" | grep -v cypress-pipe | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev)"
        sed -E 's/"cypress": "1.*"/"cypress": "'"$cypress_docker"'/g' "e2e/package.json" > e2e/packagetmp.json
        sed -E 's/"cypress": "^.*"/"cypress": "'"$cypress_docker"'/g' "e2e/packagetmp.json" > e2e/package.json
        sed -E 's/image: "cypress\/included:.*"/image: "cypress\/included:'"$cypress_docker"'/g' "e2e/docker-compose.yml" > e2e/docker-composetmp.yml
        rm e2e/packagetmp.json
        mv e2e/docker-composetmp.yml e2e/docker-compose.yml
      fi
      if [[ -d .github/workflows ]]; then
          rm .github/workflows/*.*01
          WORKFLOWS=".github/workflows/*"
          for f in $WORKFLOWS
          do
            echo "Processing $f file..."
            sed -E 's/'$oldcheckout01'/'$newcheckout'/g' "$f" > "$f""01"
            sed -E 's/'$oldcheckout02'/'$newcheckout'/g' "$f""01" > "$f"
            sed -E 's/'$oldsetupjava01'/'$newsetupjava'/g' "$f" > "$f""01"
            sed -E 's/'$oldsetupjava02'/'$newsetupjava'/g' "$f""01" > "$f"
            sed -E 's/'$oldsetupnode01'/'$newsetupnode'/g' "$f" > "$f""01"
            sed -E 's/'$oldsetupnode02'/'$newsetupnode'/g' "$f""01" > "$f"
            rm "$f""01"
          done
      fi
      cd ..
    fi
  done
  cd project-signer || exit
fi
