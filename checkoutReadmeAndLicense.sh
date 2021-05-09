#!/usr/bin/env bash

cd ..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Checkout/Reverting Readme.md and LICENSE from $item -----------------"
    git checkout 'Readme.md'
    git checkout '*/Readme.md'
    git checkout 'README.md'
    git checkout '*/README.md'
    git checkout 'LICENSE'
    git checkout '*/LICENSE'
    git checkout 'ReadMe.md'
    git checkout '*/ReadMe.md'
    cd ..
  fi
done
cd project-signer || exit
