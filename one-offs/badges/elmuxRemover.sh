#!/usr/bin/env bash
cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Removing Badge on project $item -----------------"
    find . -name "Readme.md" | while IFS= read -r readme; do
      sed -E "s/\[!\[Twitter URL](.*)//g" "$readme" > "$readme""01"
      mv "$readme""01" "$readme"
    done
    cd ..
  fi
done
cd project-signer/one-offs/decom || exit
