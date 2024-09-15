#!/usr/bin/env bash
cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Removing Badge on project $item -----------------"
    find . -name "Makefile" | while IFS= read -r readme; do
      sed -e 's|^curl -sL https://raw.githubusercontent.com/jesperancinha/project-signer/master/pluginUpdatesOne.sh \| bash$|echo "Command replaced"|g' "$readme" > "$readme""01"
      mv "$readme""01" "$readme"
    done
    cd ..
  fi
done
cd project-signer/one-offs/decom || exit
