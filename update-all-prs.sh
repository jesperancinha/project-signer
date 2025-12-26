#!/usr/bin/env bash

cd ..

user=jesperancinha
pages=100
org=JEsperancinhaOrg
porg=100

echo -e "\e[32mListing all git repos from user $user and organization $org \e[0m"
echo -e "\e[32mExample usage: ./listAllGitHub.sh jesperancinha 100 jesperancinhaOrg 100\e[0m"

git config --global pull.rebase true

# Step 1: Get current directories (assuming each repo has its own folder)
existing_folders=()
while IFS= read -r -d $'\0'; do
  existing_folders+=("$(basename "$REPLY")")
done < <(find . -mindepth 1 -maxdepth 1 -type d -print0)

# Track repos that still exist remotely
found_repos=()
parsing_result=0

add_repo() {
    local repo_name=$1
    local owner=$2
    local repo_url=$3

    found_repos+=("$repo_name")
    if [[ ! -d "$repo_name" ]]; then
      echo -e "\e[34mCloning $repo_url\e[0m"
      pwd
      gh repo clone "$owner/$repo_name"
    else
      echo -e "\e[33mSkipping $repo_name (already exists)\e[0m"
    fi
}
# Helper to process each repo JSON block
process_repo() {
  local row=$1
  local owner=$2
  local repo_name repo_url

  repo_name=$(echo "${row}" | base64 --decode | jq -r '.name') || {
     echo "Error: failed to parse repo name from JSON" >&2
     echo "${row}" | base64 --decode >&2
     echo 1
     return
  }
  repo_url=$(echo "${row}" | base64 --decode | jq -r '.ssh_url') || {
     echo "Error: failed to parse url from JSON" >&2
     echo "${row}" | base64 --decode >&2
     echo 1
     return
  }

  if [[ $(echo "${row}" | base64 --decode | jq -r '.fork') == "false" ]]; then
     add_repo "$repo_name" "$owner" "$repo_url"
  fi
  echo 0
}

# Step 2: Fetch user repos
repos=$(curl -s "https://api.github.com/users/${user}/repos?per_page=${pages}")
for row in $(echo "${repos}" | jq -r '.[] | @base64'); do
  parsing_result=$(process_repo "$row" "$user")
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
  parsing_result=$(process_repo "$row" "$org")
done

echo "$parsing_result"

# Step 4: Remove folders that are no longer in GitHub
if [[ $parsing_result -eq 0 ]]; then
  for folder in "${existing_folders[@]}"; do
    if [[ ! " ${found_repos[*]} " =~ " ${folder} " ]]; then
      echo -e "\e[31mRemoving obsolete folder: $folder\e[0m"
      rm -rf "$folder"
    fi
  done
fi

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
