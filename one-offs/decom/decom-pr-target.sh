#!/usr/bin/env bash

cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]] && [[ -d "$item/.github/workflows" ]]; then
    cd "${item}/.github/workflows" || exit
    for f in *.yml; do
      echo "Replacing $f"
      awk '/pull_request_target:/ {flag=1} flag && /- '\''\*\*'\''/ {flag=0; next} !flag' "$f" > "$f""01" && mv "$f""01" "$f"
      mv "$f""01" "$f"
    done
    cd ../../..
  fi
done
cd project-signer/one-offs/decom || exit

