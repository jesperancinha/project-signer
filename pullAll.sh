#!/usr/bin/env bash

cd ..
for item in *; do
  if [[ -d "$item" ]]; then
    cd "${item}" || exit
    echo "---*** Pulling $item ***---"
    git pull
    cd ..
  fi
done
cd project-signer || exit
