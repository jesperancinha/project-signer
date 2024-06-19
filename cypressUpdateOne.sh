#!/usr/bin/env bash

e2eFolder=${1:-"e2e"}

echo "Welcome to cypressUpdateOne.sh. This updates you container and your cypress version in your folder"
echo "There is only one input parameter. It defaults to \"e2e\" and it represents the location of your e2e tests with cypress and the docker-compose file with the cypress containers!"
echo "Performing update on folder $e2eFolder"

cd "$e2eFolder" || exit
sudo npm install -g npm-check-updates@latest
ncu -u
cd ..

cypress=$(cat < "$e2eFolder/package.json" | grep cypress | tail -1 | cut -d'"' -f4- | rev | cut -c 2- | rev | tr -d '"')
echo -e "Cypress Version"
echo -e "--- New version is \e[32m$cypress\e[0m"
echo -e "--- New docker version is \e[32m$cypress\e[0m"
sed -E 's/"cypress": .*/"cypress": "'"$cypress\""'"/g' "$e2eFolder"/package.json
sed -E 's/"cypress": "1.*"/"cypress": "'"$cypress\""'/g' "$e2eFolder/package.json" > "$e2eFolder"/packagetmp.json
sed -E 's/"cypress": "^.*"/"cypress": "'"$cypress\""'/g' "$e2eFolder/packagetmp.json" > "$e2eFolder"/package.json
sed -E 's/"cypress": ".*"/"cypress": "'"$cypress\""'/g' "$e2eFolder/packagetmp.json" > "$e2eFolder"/package.json
sed -E 's/image: "cypress\/included:.*"/image: "cypress\/included:'"$cypress\""'/g' "$e2eFolder/docker-compose.yml" > "$e2eFolder"/docker-composetmp.yml
rm "$e2eFolder"/packagetmp.json
mv "$e2eFolder"/docker-composetmp.yml "$e2eFolder"/docker-compose.yml
