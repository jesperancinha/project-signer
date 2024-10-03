#!/usr/bin/env bash

latestJavaLTS=$(curl -s https://api.adoptopenjdk.net/v3/info/available_releases | jq '.most_recent_lts')
distribution="adopt"
targetImage="eclipse-temurin:21-alpine"
targetGradleImage="gradle:jdk21"

if [[ -n $latestJavaLTS ]]; then

  #  Gradle Java - Version
  echo "Scanning for .java-version files..."
  f=".java-version"
  if [ -f $f ]; then
    sed -E "s/[0-9]*/$latestJavaLTS/g" "$f" > "$f""01"
    mv "$f""01" "$f"
  fi

  #  Gradle Files
  echo "Scanning for build.gradle and build.gradle.kts files..."
  for f in $(find . -name "build.gradle*"); do
      sed -E 's/java\.sourceCompatibility\s*=\s*JavaVersion\.VERSION_[0-9]*/java.sourceCompatibility = JavaVersion.VERSION_'"$latestJavaLTS"'/g' "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/jvmTarget\s*=\s* \"[0-9]*\"/jvmTarget = \"$latestJavaLTS\"/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/jvmTarget\.set\(JVM_[0-9]*\)/jvmTarget.set(JVM_$latestJavaLTS)/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/jvmToolchain\([0-9]*\)/jvmToolchain($latestJavaLTS)/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/languageVersion = JavaLanguageVersion.of\([0-9]*\)/languageVersion = JavaLanguageVersion.of($latestJavaLTS)/g" "$f" > "$f""01"
      mv "$f""01" "$f"
  done

  #  POM Files
  echo "Scanning for pom.xml files..."
  for f in $(find . -name "pom.xml"); do
      sed -E "s/<java\.version>[0-9]*<\/java\.version>/<java.version>$latestJavaLTS<\/java.version>/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/<java\.jdk>[0-9]*<\/java\.jdk>/<java.jdk>$latestJavaLTS<\/java.jdk>/g" "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E "s/<maven\.compiler\.source>[0-9]*<\/maven\.compiler\.source>/<maven.compiler.source>$latestJavaLTS<\/maven.compiler.source>/g" "$f" > "$f""01"
      mv "$f""01" "$f"
  done

  #  Docker Files
  echo "Scanning for Docker files..."
  for f in $(find . -name "Dockerfile"); do
      sed -E 's/FROM .*jdk.*/FROM '"$targetImage"'/g' "$f" > "$f""01"
      mv "$f""01" "$f"
      sed -E 's/FROM .*jre.*/FROM '"$targetImage"'/g' "$f" > "$f""01"
      mv "$f""01" "$f"
  done

  #  Circle CI File
  f=".circleci/config.yml"
  if [ -f $f ]; then
    targetImageUrl=$(curl -s curl -s "https://api.adoptium.net/v3/assets/latest/$latestJavaLTS/hotspot" | jq -r '[.[] | select(.binary.image_type == "jdk" and .binary.os == "linux" and .binary.architecture == "x64")][0].binary.package.link')
    targetImageUrl=${targetImageUrl//\//\\/}
    sed -E 's/-\s*image:\s*gradle.*/- image: '"$targetGradleImage"'/g' "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E 's/-\s*image:\s*maven.*/- image: '"$targetImage"'/g' "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E '/gradle/!s/-\s*image:\s*.*jdk.*/- image: '"$targetImage"'/g' "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E 's/command:\s*wget\s*https:\/\/.* &&/command: wget '"$targetImageUrl"' \&\&/g' "$f" > "$f""01"
    mv "$f""01" "$f"
    fileName=$(echo "$targetImageUrl" | awk -F'/' '{print $NF}')
    sed -E 's/tar (-)?xvf .* &&/tar -xvf '"$fileName"' \&\&/g' "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E 's/jdk[0-9]+/jdk'"$latestJavaLTS"'/g' "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E 's/jdk-[0-9]+/jdk-'"$latestJavaLTS"'/g' "$f" > "$f""01"
    mv "$f""01" "$f"
    sed -E 's/Install SDK [0-9]*/Install SDK '"$latestJavaLTS"'/g' "$f" > "$f""01"
    mv "$f""01" "$f"
  fi

else
  echo "Unable to read latest Java LTS version!"
fi
