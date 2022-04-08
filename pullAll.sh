#!/usr/bin/env bash

cd ..
for item in *; do
  if [[ -d "$item" ]]; then
    cd "${item}" || exit
    echo "---*** Pulling $item ***---"
    git pull
    git fetch -p
    git gc --prune
    cd ..
  fi
done
cd project-signer || exit
