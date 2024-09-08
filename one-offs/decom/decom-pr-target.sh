#!/usr/bin/env bash

cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]] && [[ -d "$item/.github/workflows" ]]; then
    cd "${item}/.github/workflows" || exit
    for f in *.yml; do
      echo "Replacing $f"
      sed -i '/pull_request_target:/ {N; /branches:/ {N; /- '\''\*\*'\''/d}}' $f
    done
    cd ../../..
  fi
done
cd project-signer/one-offs/decom || exit

