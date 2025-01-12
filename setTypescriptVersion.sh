#!/bin/bash

stableVersion="<5.7.0"

remote_name="origin"
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
    find . -name "package.json" -not -path "*/node_modules/*" | while read -r file; do
      echo "Processing $file"
      sed -i.bak -E 's/"typescript":\s*"<?~?([0-9]+\.)?([0-9]+\.)?[0-9]+"/"typescript": "'$stableVersion'"/' "$file"
      echo "Updated $file"
    done
    find . -name "package.json.bak" -not -path "*/node_modules/*" -delete
    git add .
    git commit -am "Typescript Update to $stableVersion"
    git push
    gh pr merge $(gh pr list --base "${master_branch}" --head "${branch_name}" --json number --jq '.[0].number' | xargs echo) --auto --merge
    git checkout "${master_branch}"
  fi
done
