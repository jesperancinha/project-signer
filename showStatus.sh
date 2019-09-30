#!/usr/bin/env bash

cd ..
for item in *; do
    cd "$item"
    echo "----------------- Status for repo $item -----------------"
    git status
    cd ..
done
cd project-signer
