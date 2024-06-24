#!/usr/bin/env bash

sudo apt -y install libxml2-utils

newVersion=$(curl -s 'https://dl.google.com/dl/android/maven2/androidx/compose/compiler/compiler/maven-metadata.xml' | xmllint --xpath 'string(//metadata/versioning/latest)' -)

echo "Updating JetPack compose compiler to version $newVersion"

for file in $(find . -name "build.gradle.kts"); do
  echo "Updating file $file"
  sed -E 's/kotlinCompilerExtensionVersion = \".*\"/kotlinCompilerExtensionVersion = \"'$newVersion'\"/g' "$file" > "$file""01"
  mv "$file""01" "$file"
done
