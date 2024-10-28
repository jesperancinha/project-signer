#!/usr/bin/env bash

cd ..
for item in *; do
  if [[ -d "$item" ]]; then
    cd "${item}" || exit
    git fetch -p
    echo "---*** List status of PRs and branches in $item ***---"
    for branch_name in $(git branch -r | grep -v '\->' | sed 's/origin\///' | grep -v 'master' | grep -v 'main' | grep -v 'migration-to-kotlin'); do
      echo "- Project $item, Branch exists!: $branch_name"
    done
    if git status | grep -q "nothing to commit, working tree clean" ; then
        echo "- Nothing to commit!";
    else
        git status;
    fi
    cd ..
  fi
done
cd project-signer || exit
