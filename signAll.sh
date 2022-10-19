#!/usr/bin/env bash

if [[ -z "$1" ]]; then
  commitMessage="Project Signing - Auto generated commit"
else
  commitMessage=$1
fi
echo -e '\e[31mWARNING!!\e[0m'
echo -e '\e[32mCarefull when using this script. It is ONLY meant to be used as a signing off tool \nfor ALL repos in your root folder \(..\)\e[0m'
echo -e "This will automatically commit EVERYTHING under ALL your repos with commit message \e[32m\"${commitMessage}\"\e[0m"
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  cd ..
  for item in *; do
    if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
      cd "${item}" || exit
      year="$(git log --reverse | sed -n -e "3,3p" | sed 's/\(.*\)\ \([0-9]*\)\ \(.*\)/\2/')"
      find . -iname "License" | xargs sed -i "" -e 's/\[yyyy\]/'${year}'/g'
#      find . -iname "License" | xargs -I {} sed -i -e "s/\[yyyy\]/'${year}'/g" {} {}
      echo "----------------- Signing (committing) ${item} -----------------"
      git add .
      if [[ -z "$1" ]]; then
        echo "no commit message. defaulting to auto"
      else
        echo "commit message ${commitMessage}"
      fi
      git config pull.rebase false
      git pull
      git commit -a -m "${commitMessage}"
      git push
      cd ..
    fi
  done
  cd project-signer || exit
fi
