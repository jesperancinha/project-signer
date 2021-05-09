#!/usr/bin/env bash

cd ..
for item in *; do
  cd "$item"
  echo "----------------- Remotes for repo $item -----------------"
  git remote show origin
  cd ..
done
cd project-signer
