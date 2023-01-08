#!/usr/bin/env bash

echo -e '\e[31m'----****!!!!! WARNING \!!!!!****----'\e[0m'
echo -e '\e[32mCareful when using this script!!\e[0m'
echo -e '\e[32mIt will remove all git ignored files and checkout all repos throwing away all changes\e[0m'
echo -e '\e[32mThe project-signer project will remain unchanged\e[0m'
echo -e "\e[31mMake sure you know what you are doing!\e[0m"
echo -e "If you are even slightly unsure, then \e[31mDO NOT RUN THIS SCRIPT!!!\e[0m."
echo -e "Type Nn or Ctr-C to leave."
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  cd ..
  for item in *; do
    if [[ -d "$item" ]]; then
      if [[ "$item" != "project-signer" ]]; then
        cd "${item}" || exit
        echo -e "\n\e[31m---*** Cleaning $item ***---\e[0m"
        echo -e "\n\e[31m---*** Cleaning non versioned files ***---\e[0m"
        git clean -xdf
        git reset
        git checkout .
        cd ..
      fi
    fi
  done
  cd project-signer || exit
fi