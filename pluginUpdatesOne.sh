#!/usr/bin/env bash

separator="/"

declare -A arr
declare -A pluginsArr

pluginsArr[0]="actions/checkout"
pluginsArr[1]="actions/setup-java"
pluginsArr[2]="actions/setup-node"
pluginsArr[3]="gradle/gradle-build-action"
pluginsArr[4]="nick-fields/retry"
pluginsArr[5]="peter-evans/create-pull-request"
pluginsArr[6]="dependabot/fetch-metadata"
pluginsArr[7]="github/codeql-action/init"

for plugin in "${pluginsArr[@]}"; do
  tag=$(echo $plugin | awk -F"$separator" '{print $1}')
  name=$(echo $plugin | awk -F"$separator" '{print substr($0, index($0,$2))}')
  echo "$tag"/"$name"
  versionUrl="https://api.github.com/repos/"$tag"/"$name"/tags"
  echo "Performing GET request to $versionUrl"
  sleep 1
  result=$(curl -s $versionUrl)
  echo "$result"
  name=${name//\//\\/}
  version=$( echo "$result" | jq -r '.[0].name' | cut -d '.' -f1)
  if [[ -n $version ]]; then
    arr["$tag\/$name@v[0-9\.]*"]="$tag\/$name@$version"
    echo "Will replace $tag\/$name@v[0-9]* with $tag\/$name@$version"
  else
    echo "ERROR! Failed to get $plugin version!"
  fi
done

echo -e "GitHub Workflow Updates"
for key in "${!arr[@]}"; do
  if [[ -n "${arr[${key}]}" ]]; then
    echo -e "--- Update \e[32m$key\e[0m to \e[32m${arr[${key}]}\e[0m"
  fi
done
if [ -d .github/workflows ]; then
  WORKFLOWS=".github/workflows/*"
  for f in $WORKFLOWS
  do
    echo "Processing $f file..."
    for key in "${!arr[@]}"; do
      if [[ -n "${arr[${key}]}" ]]; then
        echo -e "--- Updating \e[32m$key\e[0m to \e[32m${arr[${key}]}\e[0m"
        sed -E 's/'$key'/'"${arr[${key}]}"'/g' "$f" > "$f""01"
        mv "$f""01" "$f"
      fi
    done
    if [ -f "$f""01" ]; then rm "$f""01"; fi
  done
fi
