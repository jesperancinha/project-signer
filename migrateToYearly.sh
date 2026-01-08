#!/usr/bin/env bash

set -euo pipefail

find . -type f \( -name "dependabot.yml" -o -name "dependabot.yaml" \) \
  -exec sed -i.bak 's/interval:[[:space:]]*"daily"/interval: "yearly"/g' {} +
#!/usr/bin/env bash

# Go through all workflow YAML files in .github/workflows
find . -type f -path "*/.github/workflows/*.yml" -o -path "*/.github/workflows/*.yaml" | while read -r file; do
    echo "Updating $file"
    sed -i.bak "s/cron:[[:space:]]*'0 0 \* \* 0'/cron: '0 0 1 1 *'/g" "$file"
done

# Optional: remove backup files
find . -type f -name "*.yml.bak" -print0 | xargs -0 rm -f
