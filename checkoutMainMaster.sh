#!/usr/bin/env bash

cd ..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Checkout/Reverting LICENSE from $item -----------------"
    git checkout main >> /dev/null
    git checkout master
    cd ..
  fi
done
cd project-signer || exit
