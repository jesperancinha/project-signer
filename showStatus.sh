#!/usr/bin/env bash

cd ..
for item in *; do
   if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
      cd "$item" || exit
      echo "----------------- Status for repo $item -----------------"
      git status
      cd ..
   fi
done
cd project-signer || exit
