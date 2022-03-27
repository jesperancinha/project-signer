#!/usr/bin/env bash

echo -e '\e[31m'----**** WARNING!! ****----'\e[0m'
echo -e '\e[32m'Carefull when using this script. It will remove all known binaries using the following directories as references:'\e[0m'
echo -e "\e[33mtarget\e[0m"
echo -e "\e[33mnode_modules\e[0m"
echo -e "This will be done using command \e[31mrm -rf\e[0m!"
echo -e "\e[31mMake sure you know what you are doing!\e[0m"
echo -e "If you are even slightly unsure, then \e[31mDO NOT RUN THIS SCRIPT!!!\e[0m."
echo -e "Type Nn or Ctr-C to leave."
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  cd ..
  for item in *; do
    if [[ -d "$item" ]]; then
      cd "${item}" || exit
      echo "---*** Cleaning $item ***---"
      find . -type d -name 'target' | xargs -I {} ls {}/../pom.xml 2> /dev/null | sed -r 's/\/..\/pom.xml//g' | xargs rm -rf
      find . -type d -name 'node_modules' | xargs rm -rf
      cd ..
    fi
  done
  cd project-signer || exit
fi