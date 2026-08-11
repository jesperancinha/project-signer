#!/usr/bin/env bash

set -euo pipefail

SEARCH='docker-compose '
REPLACE='docker compose '

find ../.. -type f -print0 |
while IFS= read -r -d '' file; do
    # Only process text files
    if file --brief --mime-type "$file" | grep -q '^text/'; then
        if grep -qF "$SEARCH" "$file"; then
            echo "Updating: $file"
            sed -i "s/${SEARCH}/${REPLACE}/g" "$file"
        fi
    fi
done