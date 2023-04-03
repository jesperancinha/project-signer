#!/usr/bin/env bash

cd ..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Checkout $item -----------------"
    git checkout '.'
    cd ..
  fi
done
cd project-signer || exit
