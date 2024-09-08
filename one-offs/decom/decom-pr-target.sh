#!/usr/bin/env bash

cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]] && [[ -d "$item/.github/workflows" ]]; then
    cd "${item}/.github/workflows" || exit
    for f in *.yml; do
      echo "Replacing $f"
#      sed -E "s/\s*pull_request_target:\n\s*branches:\n\s*-\s*'\*\*'//g" "$f" > "$f""01"
      sed -E "s/pull_request_target://g" "$f" > "$f""01"
      mv "$f""01" "$f"
    done
    cd ../../..
  fi
done
cd project-signer/one-offs/decom || exit

