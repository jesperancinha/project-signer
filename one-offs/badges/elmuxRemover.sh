#!/usr/bin/env bash
f="Readme.md"
cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Removing Badge on project $item -----------------"
    if [ -f Readme.md ]; then
      sed -E "s/\[!\[Twitter URL](.*)//g" "$f" > "$f""01"
      mv "$f""01" "$f"
    fi
    cd ..
  fi
done
cd project-signer/one-offs/decom || exit
