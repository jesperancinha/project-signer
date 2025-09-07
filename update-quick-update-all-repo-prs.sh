#!/usr/bin/env bash

cd ..

user=jesperancinha
pages=100
org=JEsperancinhaOrg
porg=100


separator="/"

declare -A arr
declare -A pluginsArr
declare -A pluginsReplaceArr
declare -A versions

latestGradle=$(curl -s https://services.gradle.org/versions/current | jq -r '.version')
latestJavaLTS=$(curl -s https://api.adoptopenjdk.net/v3/info/available_releases | jq '.most_recent_lts')
distribution="adopt"

pluginsArr[0]="actions/checkout"
pluginsArr[1]="actions/setup-java"
pluginsArr[2]="actions/setup-node"
pluginsArr[3]="gradle/gradle-build-action"
pluginsArr[4]="nick-fields/retry"
pluginsArr[5]="peter-evans/create-pull-request"
pluginsArr[6]="dependabot/fetch-metadata"
pluginsArr[7]="github/codeql-action"
pluginsArr[8]="graalvm/setup-graalvm"
pluginsArr[9]="actions/setup-haskell"

pluginsReplaceArr[${pluginsArr[0]}]=${pluginsArr[0]}
pluginsReplaceArr[${pluginsArr[1]}]=${pluginsArr[1]}
pluginsReplaceArr[${pluginsArr[2]}]=${pluginsArr[2]}
pluginsReplaceArr[${pluginsArr[3]}]=${pluginsArr[3]}
pluginsReplaceArr[${pluginsArr[4]}]=${pluginsArr[4]}
pluginsReplaceArr[${pluginsArr[5]}]=${pluginsArr[5]}
pluginsReplaceArr[${pluginsArr[6]}]=${pluginsArr[6]}
pluginsReplaceArr[${pluginsArr[7]}]="github/codeql-action/init github/codeql-action/autobuild github/codeql-action/analyze"
pluginsReplaceArr[${pluginsArr[8]}]=${pluginsArr[8]}
pluginsReplaceArr[${pluginsArr[9]}]=${pluginsArr[9]}

if [ "$#" -eq 9 ]; then
    versions[${pluginsArr[0]}]=$1
    versions[${pluginsArr[1]}]=$2
    versions[${pluginsArr[2]}]=$3
    versions[${pluginsArr[3]}]=$4
    versions[${pluginsArr[4]}]=$5
    versions[${pluginsArr[5]}]=$6
    versions[${pluginsArr[6]}]=$7
    versions[${pluginsArr[7]}]=$8
    versions[${pluginsArr[8]}]=$9
    versions[${pluginsArr[9]}]=${10}
    for plugin in "${pluginsArr[@]}"; do
      tag=$(echo $plugin | awk -F"$separator" '{print $1}')
      name=$(echo $plugin | awk -F"$separator" '{print substr($0, index($0,$2))}')
#      echo "$tag"/"$name"
      pluginSegments=${pluginsReplaceArr[$plugin]}
      for pluginSement in $pluginSegments; do
        tag=$(echo "$pluginSement" | awk -F"$separator" '{print $1}')
        name=$(echo "$pluginSement" | awk -F"$separator" '{print substr($0, index($0,$2))}')
        name=${name//\//\\/}
#        echo "$tag"/"$name"
        version=${versions[${plugin}]}
        arr["$tag\/$name@v[0-9\.]*"]="$tag\/$name@$version"
#        echo "Will replace $tag\/$name@v[0-9]* with $tag\/$name@$version"
      done
    done
else
  for plugin in "${pluginsArr[@]}"; do
    tag=$(echo $plugin | awk -F"$separator" '{print $1}')
    name=$(echo $plugin | awk -F"$separator" '{print substr($0, index($0,$2))}')
#    echo "$tag"/"$name"

    versionUrl="https://api.github.com/repos/"$tag"/"$name"/tags"
#    echo "Performing GET request to $versionUrl"
    sleep 1
    result=$(curl -s $versionUrl)
#    echo "$result"

    pluginSegments=${pluginsReplaceArr[$plugin]}
    for pluginSement in $pluginSegments; do
      tag=$(echo "$pluginSement" | awk -F"$separator" '{print $1}')
      name=$(echo "$pluginSement" | awk -F"$separator" '{print substr($0, index($0,$2))}')
      name=${name//\//\\/}
#      echo "$tag"/"$name"

      version=$(echo "$result" | jq -r '.[0].name' | cut -d '.' -f1)
      if [[ -n $version ]]; then
        if [[ $version == v* ]]; then
          versions[${plugin}]=$version
          arr["$tag\/$name@v[0-9\.]*"]="$tag\/$name@$version"
          echo "Will replace $tag\/$name@v[0-9]* with $tag\/$name@$version"
        else
          echo "Version $version of $name is not verified, the update will not continue"
          versions[${plugin}]="!"
        fi
      else
        echo "ERROR! Failed to get $plugin version!"
      fi
    done
  done
fi

echo -e "GitHub Workflow Updates"
for key in "${!arr[@]}"; do
  if [[ -n "${arr[${key}]}" ]]; then
    echo -e "--- Update \e[32m$key\e[0m to \e[32m${arr[${key}]}\e[0m"
  fi
done

echo -e "\e[32mListing all git repos from user $user and organization $org \e[0m"
echo -e "\e[32mExample usage: ./listAllGitHub.sh jesperancinha 100 jesperancinhaOrg 100\e[0m"

# Step 1: Get current directories (assuming each repo has its own folder)
existing_folders=()
while IFS= read -r -d $'\0'; do
  existing_folders+=("$(basename "$REPLY")")
done < <(find . -mindepth 1 -maxdepth 1 -type d -print0)

# Track repos that still exist remotely
found_repos=()

# Helper to process each repo JSON block
process_repo() {
  local row=$1
  local owner=$2
  local repo_name repo_url

  repo_name=$(echo "${row}" | base64 --decode | jq -r '.name')
  repo_url=$(echo "${row}" | base64 --decode | jq -r '.ssh_url')

  if [[ $(echo "${row}" | base64 --decode | jq -r '.fork') == "false" ]]; then
    found_repos+=("$repo_name")
    if [[ ! -d "$repo_name" ]]; then
      echo -e "\e[34mCloning $repo_url\e[0m"
      pwd
      gh repo clone "$owner/$repo_name"
    else
      echo -e "\e[33mSkipping $repo_name (already exists)\e[0m"
    fi
  fi
}

# Step 2: Fetch user repos
repos=$(curl -s "https://api.github.com/users/${user}/repos?per_page=${pages}")
for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
  process_repo "$row" "$user"
done

found_repos+=("jeorg-homepage")

# Clone jeorg-homepage if not present
if [[ ! -d "jeorg-homepage" ]]; then
  echo -e "\e[34mCloning jeorg-homepage manually\e[0m"
  gh repo clone "$user/jeorg-homepage"
else
  echo -e "\e[33mSkipping jeorg-homepage (already exists)\e[0m"
fi

# Step 3: Fetch org repos
repos=$(curl -s "https://api.github.com/orgs/${org}/repos?per_page=${porg}")
for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
  process_repo "$row" "$org"
done

# Step 4: Remove folders that are no longer in GitHub
for folder in "${existing_folders[@]}"; do
  if [[ ! " ${found_repos[*]} " =~ " ${folder} " ]]; then
    echo -e "\e[31mRemoving obsolete folder: $folder\e[0m"
    rm -rf "$folder"
  fi
done

remote_name="origin"
for item in *; do
  if [[ -d "$item" ]]; then
    cd "${item}" || exit
    echo "---*** Updating PRs in project $item ***---"
    echo "--- Configuring for user $user ---"
    git config --local user.email jofisaes@gmail.com
    echo "--- Configuring rebase pull false ---"
    git config pull.rebase false
    if git ls-remote --exit-code --heads "$remote_name" "master"; then
      master_branch="master"
    fi
    if git ls-remote --exit-code --heads "$remote_name" "main"; then
      master_branch="main"
    fi

    if [ -n "${master_branch}" ]; then
     git checkout "${master_branch}"
     if [ -d .github/workflows ]; then
       WORKFLOWS=".github/workflows/*"
       for f in $WORKFLOWS
       do
         echo "Processing $f file..."
         for key in "${!arr[@]}"; do
           if [[ -n "${arr[${key}]}" && "${arr[${key}]}" != "!" ]]; then
             echo -e "--- Updating \e[32m$key\e[0m to \e[32m${arr[${key}]}\e[0m"
             sed -E 's/'$key'/'"${arr[${key}]}"'/g' "$f" > "$f""01"
             mv "$f""01" "$f"
           fi
           if [[ -n $latestGradle ]]; then
             echo -e "--- Updating to gradle version \e[32m$latestGradle\e[0m"
             sed -E "s/gradle-version\: [0-9\.a-z]*/gradle-version: $latestGradle/g" "$f" > "$f""01"
             mv "$f""01" "$f"
             sed -E "s/Setup Gradle [0-9\.a-z]*/Setup Gradle $latestGradle/g" "$f" > "$f""01"
             mv "$f""01" "$f"
           fi
           if [[ -n $latestJavaLTS ]]; then
             echo -e "--- Updating to java version \e[32m$latestJavaLTS\e[0m and distribution \e[32m$distribution\e[0m"
             sed -E 's/Set up JDK [0-9]*/Set up JDK '"$latestJavaLTS"'/g' "$f" > "$f""01"
             mv "$f""01" "$f"
             sed -E "s/java-version: '[0-9]*'/java-version: '$latestJavaLTS'/g" "$f" > "$f""01"
             mv "$f""01" "$f"
             sed -E "s/distribution: '[0-9a-zA-Z]*'/distribution: '$distribution'/g" "$f" > "$f""01"
             mv "$f""01" "$f"
           fi
         done
         if [ -f "$f""01" ]; then rm "$f""01"; fi
       done
     fi
     git commit -am "Automated Update"
    fi

    git pull
    git fetch -p
    for branch_name in $(git branch -r | grep -v '\->' | sed 's/origin\///' | grep -v 'master' | grep -v 'main' | grep -v 'migration-to-kotlin' | grep -v '1.0.0-eol-continuous-release-branch-recovered' | grep -v 'migrate-to-kotlin'); do
      echo "Processing branch: $branch_name"
      if [ -n "${master_branch}" ]; then
        git checkout "${branch_name}"
        git pull
        git merge origin/"${master_branch}" --no-edit
        git push
        gh pr merge $(gh pr list --base "${master_branch}" --head "${branch_name}" --json number --jq '.[0].number' | xargs echo) --auto --merge
        git checkout "${master_branch}"
      fi
    done
    cd ..
  fi
done
cd project-signer || exit
