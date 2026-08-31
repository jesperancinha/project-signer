#!/usr/bin/env bash

set -e

read -r -p "WARNING: This command will delete PERMANENTLY your files. They WILL NOT be recoverable! (yes/no) " answer
if [[ "$answer" != "yes" ]]; then
    echo "Aborted."
    exit 1
fi

read -r -p "Did you backup all subfolders, and are you sure you don't need anything in this subset of folders? Remember that EVERYTHING will be DELETED FOREVER! (yes/no) " answer
if [[ "$answer" != "yes" ]]; then
    echo "Aborted."
    exit 1
fi

read -r -p "Sometimes there are hidden memories in some of these folders. Did you check for them? I won't ask again. This was my final warning, on another Yes, I will run a delete command where no files will ever be recoverable! (yes/no) " answer
if [[ "$answer" != "yes" ]]; then
    echo "Aborted."
    exit 1
fi

echo
echo "All confirmations received."
echo

for dir in */; do
    [[ -d "$dir" ]] || continue

    echo "========================================"
    echo "Entering: $dir"
    echo "========================================"

    (
        cd -- "$dir"
        pwd
        shred -u -z -v -n 10 .git/**/** || true
        shred -u -z -v -n 10 */**/**  || true
        sudo find . -mindepth 1 -maxdepth 1 -exec rm -rf -- {} +
        cd ..
    )
done

echo
echo "Finished."

