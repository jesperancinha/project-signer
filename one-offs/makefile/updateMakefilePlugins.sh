#!/usr/bin/env bash
cd ../../..
for item in *; do
  if [[ -d "$item" ]] && [[ "$item" != ".git" ]] && [[ "$item" != "target" ]]; then
    cd "$item" || exit
    echo "----------------- Updating Makefiles to include PARAMS $item -----------------"
    find . -name "Makefile" | while IFS= read -r f; do
      sed -E 's/curl\s-sL https\:\/\/raw\.githubusercontent\.com\/jesperancinha\/project\-signer\/master\/pluginUpdatesOne\.sh \| bash/curl -sL https\:\/\/raw\.githubusercontent\.com\/jesperancinha\/project\-signer\/master\/pluginUpdatesOne\.sh \| bash \-s \-\- $\(PARAMS)/g' "$f" > "$f""01"
      mv "$f""01" "$f"
    done
    cd ..
  fi
done
cd project-signer/one-offs/decom || exit
