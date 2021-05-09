#!/usr/bin/env bash

cd ..
for item in *; do
   if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
      cd "$item" || exit
      echo "----------------- Checkout/Reverting LICENSE from $item -----------------"
      git checkout 'LICENSE'
      git checkout '**/LICENSE'
      git checkout 'License'
      git checkout '**/License'
      cd ..
   fi
done
cd project-signer || exit
