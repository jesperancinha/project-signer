#!/usr/bin/env bash

set -euo pipefail

SEARCH='docker compose '
REPLACE='docker compose '

find .. -type f \( \
    -name '*.sh' -o \
    -name 'Makefile' -o \
    -name 'Makefile.mk' -o \
    -name '*.yml' -o \
    -name '*.yaml' \
\) -print0 |
while IFS= read -r -d '' file; do
    if grep -qF "$SEARCH" "$file"; then
        echo "Updating: $file"
        sed -i "s/${SEARCH}/${REPLACE}/g" "$file"
    fi
done