#!/usr/bin/env sh
echo "Used only to create an extra commit so that PR's created with the plugin get started"
echo "More info one:"
echo "- https://github.com/orgs/community/discussions/25702"

echoPRDate() {
  echo "PR Accept on $(date)" >> pr.md
}
git pull
LANG=en_us_8859_1
if [ ! -f pr.md ]; then
  echoPRDate
  git add pr.md
  git commit -m "Accepts Automated PRs on $(date)"
else
  echoPRDate
  git add pr.md
  git commit -m "Accepts Automated PRs on $(date)" -- pr.md
fi

git push
