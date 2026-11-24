@echo off
cd /d "%~dp0"
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-ChildItem -File | Where-Object { $_.Name -like '*__*' } | ForEach-Object { $p = Join-Path 'ATLAS_project' ($_.Name -replace '__','\'); New-Item -ItemType Directory -Force -Path (Split-Path $p -Parent) | Out-Null; Copy-Item -Force $_.FullName $p }; Copy-Item -Force 'README.md','.gitignore','build.gradle.kts','settings.gradle.kts','gradle.properties' 'ATLAS_project'"
echo.
echo Done. Open the ATLAS_project folder in Android Studio.
pause
