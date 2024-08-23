#!/usr/bin/env bash

latestNodeLTS=$(curl -s https://nodejs.org/dist/index.json | jq -r '.[] | select(.lts != false) | .version' | head -n 1 | cut -d'.' -f1 | cut -d 'v' -f2)

echo "Found node JS version $latestNodeLTS"
if [[ -n $latestNodeLTS ]]; then
  #  YML pipeline files and more - Version
  echo "Scanning for yml files including GitHub action files..."
  for f in $(find . -name "*.yml"); do
      sed -E 's/-\s*image:\s*cimg\/node.*/- image: cimg\/node:'"$latestNodeLTS"'.0.0/g' "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/node\-version\: '[0-9]*'/node-version\: '$latestNodeLTS'/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/name\: Use Node\.js\s*[0-9]*/name: Set up Node\.js $latestNodeLTS/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/name\: Set up Node\.js\s*[0-9]*/name: Set up Node\.js $latestNodeLTS/g" "$f" > "$f""01"
      mv "$f""01" "$f"
  done
else
  echo "Unable to read latest Node LTS version!"
fi
