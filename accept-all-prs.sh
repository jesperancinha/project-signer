#!/usr/bin/env bash

all_pr_branches=("update-cypress-dependencies-and-containers" "update-gradle" "update-npm-dependencies")
remote_name="origin"

cd ..
for item in *; do
  if [[ -d "$item" ]]; then
    cd "${item}" || exit
    if [ -f Makefile ]; then
      if grep -q "^accept-prs:" Makefile; then
        echo "---*** Accept PR Makefile command found in $item ***---"
        git pull
        git fetch -p
        for branch_name in "${all_pr_branches[@]}"; do
          if git ls-remote --exit-code --heads "$remote_name" "$branch_name"; then
              echo "Remote branch '$branch_name' exists on '$remote_name'."
              make accept-prs
          fi
        done
      fi
    fi

    cd ..
  fi
done
cd project-signer || exit
