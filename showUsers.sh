#!/usr/bin/env bash

cd ..
for item in *; do
    cd "$item"
    echo "----------------- User config for repo $item -----------------"
    git config user.name
    git config user.email
    cd ..
done
cd project-signer
