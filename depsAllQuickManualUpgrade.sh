#!/usr/bin/env bash

branch_name="update-quick"
remote_name="origin"

cd ..
for item in *; do
  if [[ -d "$item" ]]; then
    cd "${item}" || exit
    if [ -f Makefile ]; then
      item="this"
      if grep -q "^deps-quick-update:" Makefile; then
        echo "---*** Quick Update Makefile command found in $item ***---"
        if git show-ref --verify --quiet refs/heads/master; then
          git checkout master
        fi
        if git show-ref --verify --quiet refs/heads/main; then
          git checkout main
        fi
        git pull
        git fetch -p
        make deps-quick-update
        if ! git diff --quiet || ! git diff --cached --quiet; then
          if ! git ls-remote --exit-code --heads "$remote_name" "$branch_name"; then
            git checkout -b $branch_name
            git commit -am "quick updates"
            git push -u origin $branch_name
            if git show-ref --verify --quiet refs/heads/master; then
              git checkout master
            fi
            if git show-ref --verify --quiet refs/heads/main; then
              git checkout main
            fi
          fi
        fi
      fi
    fi

    cd ..
  fi
done
cd project-signer || exit
