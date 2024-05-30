#!/usr/bin/env bash

cypress=$(cat < "e2e/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev)
cypress_docker=$(cat < "e2e/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | cut -c 2- )


declare -A arr

arr["actions\/checkout@v1"]="actions\/checkout@v4"
arr["actions\/checkout@v2"]="actions\/checkout@v4"
arr["actions\/checkout@v3"]="actions\/checkout@v4"
arr["actions\/setup-java@v1"]="actions\/setup-java@v4"
arr["actions\/setup-java@v2"]="actions\/setup-java@v4"
arr["actions\/setup-java@v3"]="actions\/setup-java@v4"
arr["actions\/setup-node@v1"]="actions\/setup-node@v4"
arr["actions\/setup-node@v2"]="actions\/setup-node@v4"
arr["actions\/setup-node@v3"]="actions\/setup-node@v4"

echo -e "Cypress Version"
echo -e "--- New version is \e[32m$cypress\e[0m"
echo -e "--- New docker version is \e[32m$cypress_docker\e[0m"
echo -e "GitHub Workflow Updates"
for key in "${!arr[@]}"; do
    echo -e "--- Update \e[32m$key\e[0m to \e[32m${arr[${key}]}\e[0m"
done

sed -E 's/"cypress": .*/"cypress": "'"$cypress"'"/g' e2e/package.json

read -p "Are you sure? (Yy/Nn)" -n 1 -r

if [[ $REPLY =~ ^[Yy]$ ]]; then
  cd ..
  for item in *; do
    if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
      cd "${item}" || exit
      if [[ ! $item = "project-signer" ]]; then
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
      fi
      if [[ -d .github/workflows ]]; then
        rm .github/workflows/*.*01
        WORKFLOWS=".github/workflows/*"
        for f in $WORKFLOWS
        do
          echo "Processing $f file..."
          for key in "${!arr[@]}"; do
              echo -e "--- Updating \e[32m$key\e[0m to \e[32m${arr[${key}]}\e[0m"
              sed -E 's/'$key'/'${arr[${key}]}'/g' "$f" > "$f""01"
              sed -E 's/'$key'/'${arr[${key}]}'/g' "$f""01" > "$f"
          done
          rm "$f""01"
        done
      fi
      cd ..
    fi
  done
  cd project-signer || exit
fi
