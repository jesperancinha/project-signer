#!/usr/bin/env bash

set -euo pipefail

find . -type f \( -name "dependabot.yml" -o -name "dependabot.yaml" \) \
  -exec sed -i.bak 's/interval:[[:space:]]*"daily"/interval: "yearly"/g' {} +

# Optional: remove backup files
find . -type f -name "dependabot.yml.bak" -print0 | xargs -0 rm -f
