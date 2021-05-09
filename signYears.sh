#!/usr/bin/env bash

echo -e '\e[31m'WARNING!!'\e[0m'
echo -e '\e[32m'Carefull when using this script. It is ONLY meant to be used as a signing off tool \for ALL repos in your root folder \(..\)'\e[0m'
echo -e "This will automatically replace the [yyyy] in the LICENSE files"
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  cd ..
  for item in *; do
    if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
      cd "${item}"

      year="$(git log --reverse | sed -n -e "3,3p" | sed 's/\(.*\)\ \([0-9]*\)\ \(.*\)/\2/')"
      echo "----------------- Signing years in repo ${item} -----------------"
      find . -iname "License" | xargs sed -i "" 's/\[yyyy\]/'${year}'/g'

      cd ..
    fi
  done
  cd project-signer
fi
