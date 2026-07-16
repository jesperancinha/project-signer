#!/bin/bash

# This script updates .claude directories in sibling projects based on the current project's .claude content.
# It only updates existing folders and files, and removes folders that don't exist in the signer project.
# It never adds new folders or files to target projects.

SIGNER_PROJECT="project-signer"
SIGNER_DIR="$(pwd)/.claude"

if [ ! -d "$SIGNER_DIR" ]; then
    echo "Error: .claude directory not found in current project ($SIGNER_PROJECT)"
    exit 1
fi

# Iterate over projects one folder upwards
for project_path in ../*/; do
    project_name=$(basename "$project_path")
    
    # Skip the signer project
    if [ "$project_name" == "$SIGNER_PROJECT" ]; then
        continue
    fi
    
    TARGET_CLAUDE="${project_path}.claude"
    
    # Check if .claude directory exists in target project
    if [ -d "$TARGET_CLAUDE" ]; then
        echo "Updating $project_name..."
        
        # 1. Remove subfolders in target .claude that do not exist in signer .claude
        find "$TARGET_CLAUDE" -mindepth 1 -maxdepth 1 -type d | while read -r target_subfolder; do
            subfolder_name=$(basename "$target_subfolder")
            if [ ! -d "$SIGNER_DIR/$subfolder_name" ]; then
                echo "  Removing extra folder: $subfolder_name"
                rm -rf "$target_subfolder"
            fi
        done
        
        # 2. Iterate through subfolders of signer .claude to update existing counterparts
        find "$SIGNER_DIR" -mindepth 1 -maxdepth 1 -type d | while read -r signer_subfolder; do
            subfolder_name=$(basename "$signer_subfolder")
            target_subfolder="$TARGET_CLAUDE/$subfolder_name"
            
            # Only update if the subfolder exists in the target
            if [ -d "$target_subfolder" ]; then
                echo "  Updating folder: $subfolder_name"
                
                # Remove all sub-folders in target that don't exist in signer
                find "$target_subfolder" -mindepth 1 -maxdepth 1 -type d | while read -r target_deep_folder; do
                    deep_folder_name=$(basename "$target_deep_folder")
                    if [ ! -d "$signer_subfolder/$deep_folder_name" ]; then
                        echo "    Removing extra sub-folder: $subfolder_name/$deep_folder_name"
                        rm -rf "$target_deep_folder"
                    fi
                    if [ -z "$( ls -A "$target_deep_folder" )" ]; then
                        rm -rf "$target_deep_folder"
                    fi
                done

                # Sync content from signer to target subfolders
                find "$signer_subfolder" -mindepth 1 -maxdepth 1 -type d | while read -r signer_deep_folder; do
                    deep_folder_name=$(basename "$signer_deep_folder")
                    # Only copy if the folder already exists in the target
                    if [ -d "$target_subfolder/$deep_folder_name" ]; then
                         # Sync content. Using rsync if available would be better, but cp is more universal.
                         # We use -r to copy everything inside.
                         cp -r "$signer_deep_folder/." "$target_subfolder/$deep_folder_name/"
                    fi
                done

                # Update files directly in the subfolder that already exist in the target
                find "$signer_subfolder" -mindepth 1 -maxdepth 1 -type f | while read -r signer_file; do
                    file_name=$(basename "$signer_file")
                    if [ -f "$target_subfolder/$file_name" ]; then
                        cp "$signer_file" "$target_subfolder/$file_name"
                    fi
                done
            fi
        done
        
        # 3. Update files directly in .claude if they exist in both
        find "$SIGNER_DIR" -mindepth 1 -maxdepth 1 -type f | while read -r signer_file; do
            file_name=$(basename "$signer_file")
            if [ -f "$TARGET_CLAUDE/$file_name" ]; then
                echo "  Updating file: $file_name"
                cp "$signer_file" "$TARGET_CLAUDE/$file_name"
            fi
        done
    fi
done

echo "Done."
