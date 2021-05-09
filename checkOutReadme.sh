#!/usr/bin/env bash

cd ..
for item in *; do
 if [[ -d "$item" ]]; then
    cd "${item}"
    git checkout **/README.*
    git checkout **/Readme.*
    git checkout README.*
    git checkout Readme.*
    git checkout **/LICENSE.*
    git checkout LICENSE.*
    cd ..
 fi
done
cd project-signer