#!/usr/bin/env bash

cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "${item}" || exit
    echo "Backing-up ${item}..."
    mkdir -p backup/travis
    mv .travis.yml backup/travis
    echo "Backing-up ${item}...Done!"
    cd ..
  fi
done
cd project-signer/one-offs/decom || exit
