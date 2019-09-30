#!/usr/bin/env bash

echo -e '\e[31m'WARNING!!'\e[0m'
echo -e '\e[32m'Carefull when using this script. It is ONLY meant to be used as a signing off tool \for ALL repos in your root folder \(..\)'\e[0m'
echo "This will automatically commit EVERYTHING under ALL your repos with commit message \"Project Signing - Auto generated commit\""
read -p "Are you sure? (Yy/Nn)" -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]
then
    cd ..
    for item in *; do
        cd "$item"
        echo "----------------- Signing (committing) $item -----------------"
        git add .
        git commit -a -m "Project Signing - Auto generated commit"
        git push
        cd ..
    done
    cd project-signer
fi