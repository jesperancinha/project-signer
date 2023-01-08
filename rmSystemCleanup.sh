#!/usr/bin/env bash

#TODO: rm ~/Library/Containers/com.docker.docker/Data/vms/0/data/

echo -e '\e[31m'----****!!!!! WARNING \!!!!!****----'\e[0m'
echo -e '\e[32mCareful when using this script!!\e[0m'
echo -e '\e[32mIt will remove all known binaries in you maven folder (assuming you are using ~/.m2)\e[0m'
echo -e '\e[32mIt will prune all docker created elements\e[0m'
echo -e '\e[32mIt will remove everything in the /tmp folder (assuming you have it)\e[0m'
echo -e '\e[32mIt will remove all known binaries of the projects located in ../ (and subs) using the following directories as references:\e[0m'
echo -e "\e[33m- target\e[0m"
echo -e "\e[33m- node_modules\e[0m"
echo -e "This will be done using command \e[31mrm -rf\e[0m!"
echo -e "\e[31mMake sure you know what you are doing!\e[0m"
echo -e "If you are even slightly unsure, then \e[31mDO NOT RUN THIS SCRIPT!!!\e[0m."
echo -e "Type Nn or Ctr-C to leave."
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  echo -e "\n\e[31m---*** Pruning /tmp folder ***---\e[0m"
  sudo rm -rf /tmp/*
  echo -e "\n\e[31m---*** Pruning all Docker elements ***---\e[0m"
  make prune-all
  echo -e "\n\e[31m---*** Cleaning ~/.m2/repository ***---\e[0m"
  sudo rm -rf ~/.m2/repository/*
  cd ..
  for item in *; do
    if [[ -d "$item" ]]; then
      cd "${item}" || exit
      echo -e "\n\e[31m---*** Cleaning $item ***---\e[0m"
      echo -e "\n\e[31m---*** Compressing Repo ***---\e[0m"
      git gc --aggressive
      echo -e "\n\e[31m---*** Removing target(s) folder(s) ***---\e[0m"
      find . -type d -name 'target' | xargs -I {} ls {}/../pom.xml 2> /dev/null | sed -r 's/\/..\/pom.xml//g' | xargs rm -rf
      echo -e "\n\e[31m---*** Removing node_modules folder(s) ***---\e[0m"
      find . -type d -name 'node_modules' | xargs rm -rf
      echo -e "\n\e[31m---*** Cleaning non versioned files ***---\e[0m"
      git clean -xdf
      git checkout .
      cd ..
    fi
  done
  cd project-signer || exit
fi