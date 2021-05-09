#!/usr/bin/env bash

cd ..
for item in *; do
 if [[ -d "$item" ]]; then
    cd "${item}"
    git checkout **/README.*
    git checkout **/Readme.*
    git checkout README.*
    git checkout Readme.*
    cd ..
 fi
done
cd project-signer