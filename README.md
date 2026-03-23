# ZombieDeck
[![Android Instrumented Tests](https://github.com/dapitch666/ZombieDeck/actions/workflows/android_instrumented_tests.yaml/badge.svg)](https://github.com/dapitch666/ZombieDeck/actions/workflows/android_instrumented_tests.yaml)
[![Unit Tests](https://github.com/dapitch666/ZombieDeck/actions/workflows/unit_tests.yaml/badge.svg)](https://github.com/dapitch666/ZombieDeck/actions/workflows/unit_tests.yaml)
[![Release APK](https://github.com/dapitch666/ZombieDeck/actions/workflows/release_apk.yaml/badge.svg)](https://github.com/dapitch666/ZombieDeck/actions/workflows/release_apk.yaml)

## Description

ZombieDeck is an Android companion app that helps players run the zombie spawn deck from Zombicide 2nd Edition game.
It lets you quickly draw cards, track danger level, and tune deck difficulty with optional expansions.

## APK installation

### From GitHub Releases

1. Download the latest APK from the project Releases page.
2. On your Android device, allow installing apps from unknown sources.
3. Open the APK and complete the installation.

### From local build (ADB)

```bash
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Setup

Prerequisites:

- Android Studio (recent stable version)
- Android SDK Platform 36 (project `compileSdk = 36`)
- JDK 21 (project Java/Kotlin toolchain)

Project setup:

```bash
git clone https://github.com/dapitch666/ZombieDeck.git
cd ZombieDeck
./gradlew tasks
```

Then open the project in Android Studio and let Gradle sync.

## Build

```bash
./gradlew :app:assembleDebug
./gradlew :app:assembleRelease
```

## Versioning

Application version is managed in `version.properties`:

```properties
VERSION_NAME=1.0.0
VERSION_CODE=1
```

Useful Gradle commands:

```bash
./gradlew printVersion
./gradlew bumpPatch
./gradlew bumpMinor
./gradlew bumpMajor
```

Automated release commands:

```bash
./gradlew releasePatch
./gradlew releaseMinor
./gradlew releaseMajor
```

Each `release*` task checks for a clean working tree, bumps version, commits `version.properties`, creates tag `vX.Y.Z`, and pushes both branch and tag to `origin`.

Release signing is enabled when these environment variables are provided:

- `KEYSTORE_PATH`
- `KEYSTORE_PASSWORD`
- `KEY_ALIAS`
- `KEY_PASSWORD`

## Test

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:connectedDebugAndroidTest
./gradlew :app:lintDebug
```

## Contribution

1. Fork the repository and create a branch from `main`.
2. Make focused changes with clear commit messages.
3. Run unit tests and lint locally before opening a PR.
4. Open a Pull Request with context, screenshots (if UI changes), and test notes.

## License

This project is distributed under the terms described in the `LICENSE` file.

## Publish APK to GitHub

- Create these GitHub Actions secrets before publishing:
  - `ANDROID_KEYSTORE_BASE64`
  - `KEYSTORE_PASSWORD`
  - `KEY_ALIAS`
  - `KEY_PASSWORD`
- Run a Gradle release task (for example `./gradlew releasePatch`).
- The pushed tag triggers the workflow, builds a signed release APK, uploads artifacts, and publishes a GitHub Release.
