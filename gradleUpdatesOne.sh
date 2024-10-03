#!/usr/bin/env bash

latestGradle=$(curl -s https://services.gradle.org/versions/current | jq -r '.version')

if [[ -n $latestGradle ]]; then

  #  Makefile Gradle parent GRADLE_VERSION
  echo "Scanning Makefile"
  f="Makefile"
  if [ -f $f ]; then
    sed -E "s/GRADLE_VERSION \?= [0-9\.a-z]*/GRADLE_VERSION ?= $latestGradle/g" "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E "s/GRADLE_VERSION \:= [0-9\.a-z]*/GRADLE_VERSION ?= $latestGradle/g" "$f" > "$f""01"
    mv "$f""01" "$f"
  fi
  #  Makefile.mk Gradle parent GRADLE_VERSION
  echo "Scanning Makefile"
  f="Makefile.mk"
  if [ -f $f ]; then
    sed -E "s/GRADLE_VERSION \?= [0-9\.a-z]*/GRADLE_VERSION ?= $latestGradle/g" "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E "s/GRADLE_VERSION \:= [0-9\.a-z]*/GRADLE_VERSION ?= $latestGradle/g" "$f" > "$f""01"
    mv "$f""01" "$f"
  fi

  # Gradle Wrapper
  echo "Scanning for gradle-wrapper.properties files..."
  for f in $(find . -name "gradle-wrapper.properties"); do
      sed -E 's/services\.gradle\.org\/distributions\/gradle\-[0-9\.]*\-bin\.zip/services\.gradle\.org\/distributions\/gradle\-'"$latestGradle"'-bin\.zip/g' "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E 's/FROM .*jre.*/FROM '"$targetImage"'/g' "$f" > "$f""01"
      mv "$f""01" "$f"
  done

  if [ -d build ]; then rm -r build; fi
else
  echo "Unable to read latest Java LTS version!"
fi
