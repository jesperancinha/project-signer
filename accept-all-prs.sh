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
        for branch_name in "${all_pr_branches[@]}"; do
          if git ls-remote --exit-code --heads "$remote_name" "$branch_name"; then
              echo "Remote branch '$branch_name' exists on '$remote_name'."
              make accept-prs
              if [ -n "${master_branch}" ]; then
                git checkout "${branch_name}"
                git pull
                git merge origin/"${master_branch}" --no-edit
                git push
                gh pr merge $(gh pr list --base "${master_branch}" --head "${branch_name}" --json number --jq '.[0].number' | xargs echo) --auto --merge
                git checkout "${master_branch}"
              fi
          fi
        done
      fi
    fi

    cd ..
  fi
done
cd project-signer || exit
