#!/usr/bin/env bash

set -euo pipefail

SEARCH='docker-compose '
REPLACE='docker compose '
SEARCH2='docker-compose.yml'
REPLACE2='docker-compose.yml'

find ../.. -type f \( \
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
    if grep -qF "$SEARCH2" "$file"; then
        echo "Updating: $file"
        sed -i "s/${SEARCH2}/${REPLACE2}/g" "$file"
    fi
done