#!/usr/bin/env bash

cd ..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Checkout/Reverting $item -----------------"
    git reset
    git checkout '.'
    git pull
    git fetch -p
    cd ..
  fi
done
cd project-signer || exit
