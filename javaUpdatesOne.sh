#!/usr/bin/env bash

latestJavaLTS=$( curl -s https://api.adoptopenjdk.net/v3/info/available_releases | jq '.most_recent_lts')
distribution="adopt"

if [[ -n $latestJavaLTS ]]; then
  for f in $(find . -name "*.yml"); do
      sed -E 's/Set up JDK [0-9]*/Set up JDK '"$latestJavaLTS"'/g' "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/java-version: '[0-9]*'/java-version: '$latestJavaLTS'/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/distribution: '[0-9a-zA-Z]*'/distribution: '$distribution'/g" "$f" > "$f""01"
      mv "$f""01" "$f"
  done

  f=".java-version"
  if [ -f $f ]; then
    sed -E "s/[0-9]*/$latestJavaLTS/g" "$f" > "$f""01"
    mv "$f""01" "$f"
  fi

  for f in $(find . -name "build.gradle*"); do
      sed -E 's/java\.sourceCompatibility\s*=\s*JavaVersion\.VERSION_[0-9]*/java.sourceCompatibility = JavaVersion.VERSION_'"$latestJavaLTS"'/g' "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/jvmTarget\s*=\s* \"[0-9]*\"/jvmTarget = \"$latestJavaLTS\"/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/languageVersion = JavaLanguageVersion.of\([0-9]*\)/languageVersion = JavaLanguageVersion.of($latestJavaLTS)/g" "$f" > "$f""01"
      mv "$f""01" "$f"
  done

  for f in $(find . -name "pom.xml"); do
      sed -E "s/<java\.version>[0-9]*<\/java\.version>/<java.version>$latestJavaLTS<\/java.version>/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/<java\.jdk>[0-9]*<\/java\.jdk>/<java.jdk>$latestJavaLTS<\/java.jdk>/g" "$f" > "$f""01"
      mv "$f""01" "$f"
  done
else
  echo "Unable to read latest Java LTS version!"
fi
