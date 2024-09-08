#!/usr/bin/env bash

separator="/"

declare -A arr
declare -A pluginsArr
declare -A pluginsReplaceArr

pluginsArr[0]="actions/checkout"
pluginsArr[1]="actions/setup-java"
pluginsArr[2]="actions/setup-node"
pluginsArr[3]="gradle/gradle-build-action"
pluginsArr[4]="nick-fields/retry"
pluginsArr[5]="peter-evans/create-pull-request"
pluginsArr[6]="dependabot/fetch-metadata"
pluginsArr[7]="github/codeql-action"
pluginsArr[8]="graalvm/setup-graalvm"

pluginsReplaceArr[${pluginsArr[0]}]=${pluginsArr[0]}
pluginsReplaceArr[${pluginsArr[1]}]=${pluginsArr[1]}
pluginsReplaceArr[${pluginsArr[2]}]=${pluginsArr[2]}
pluginsReplaceArr[${pluginsArr[3]}]=${pluginsArr[3]}
pluginsReplaceArr[${pluginsArr[4]}]=${pluginsArr[4]}
pluginsReplaceArr[${pluginsArr[5]}]=${pluginsArr[5]}
pluginsReplaceArr[${pluginsArr[6]}]=${pluginsArr[6]}
pluginsReplaceArr[${pluginsArr[7]}]="github/codeql-action/init github/codeql-action/autobuild github/codeql-action/analyze"
pluginsReplaceArr[${pluginsArr[8]}]=${pluginsArr[8]}

for plugin in "${pluginsArr[@]}"; do

  tag=$(echo $plugin | awk -F"$separator" '{print $1}')
  name=$(echo $plugin | awk -F"$separator" '{print substr($0, index($0,$2))}')
  echo "$tag"/"$name"
  versionUrl="https://api.github.com/repos/"$tag"/"$name"/tags"
  echo "Performing GET request to $versionUrl"
  sleep 1
  result=$(curl -s $versionUrl)
  echo "$result"

  pluginSegments=${pluginsReplaceArr[$plugin]}
  for pluginSement in $pluginSegments; do
    tag=$(echo "$pluginSement" | awk -F"$separator" '{print $1}')
    name=$(echo "$pluginSement" | awk -F"$separator" '{print substr($0, index($0,$2))}')
    name=${name//\//\\/}
    echo "$tag"/"$name"

    version=$(echo "$result" | jq -r '.[0].name' | cut -d '.' -f1)
    if [[ -n $version ]]; then
      if [[ $version == v* ]]; then
        arr["$tag\/$name@v[0-9\.]*"]="$tag\/$name@$version"
        echo "Will replace $tag\/$name@v[0-9]* with $tag\/$name@$version"
      else
        echo "Version $version of $name is not verified, the update will not continue"
      fi
    else
      echo "ERROR! Failed to get $plugin version!"
    fi
  done
done

echo -e "GitHub Workflow Updates"
for key in "${!arr[@]}"; do
  if [[ -n "${arr[${key}]}" ]]; then
    echo -e "--- Update \e[32m$key\e[0m to \e[32m${arr[${key}]}\e[0m"
  fi
done
