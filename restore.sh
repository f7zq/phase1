#!/bin/sh
# Rebuilds the real folder structure into ./ATLAS_project
cd "$(dirname "$0")" || exit 1
for f in *__*; do
  [ -f "$f" ] || continue
  p="ATLAS_project/$(printf '%s' "$f" | sed 's|__|/|g')"
  mkdir -p "$(dirname "$p")"
  cp "$f" "$p"
done
for f in README.md .gitignore build.gradle.kts settings.gradle.kts gradle.properties; do cp "$f" "ATLAS_project/$f"; done
echo "Done. Open the ATLAS_project folder in Android Studio."
