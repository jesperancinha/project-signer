#!/usr/bin/env bash
source pluginUpdatesOne.sh

# shellcheck disable=SC2154
version_command="${versions[${pluginsArr[0]}]} ${versions[${pluginsArr[1]}]} ${versions[${pluginsArr[2]}]} ${versions[${pluginsArr[3]}]} ${versions[${pluginsArr[4]}]} ${versions[${pluginsArr[5]}]} ${versions[${pluginsArr[6]}]} ${versions[${pluginsArr[7]}]} ${versions[${pluginsArr[8]}]}"
branch_name="update-quick"
remote_name="origin"

#cd ..
#for item in *; do
#  if [[ -d "$item" ]]; then
#    cd "${item}" || exit
#    if [ -f Makefile ]; then
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
        make deps-quick-update "PARAMS=${version_command}"
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
#    fi
#
#    cd ..
#  fi
#done
#cd project-signer || exit
