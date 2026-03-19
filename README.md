# ZombieDeck
[![Android Instrumented Tests](https://github.com/dapitch666/ZombieDeck/actions/workflows/android_instrumented_tests.yaml/badge.svg)](https://github.com/dapitch666/ZombieDeck/actions/workflows/android_instrumented_tests.yaml)
[![Unit Tests](https://github.com/dapitch666/ZombieDeck/actions/workflows/unit_tests.yaml/badge.svg)](https://github.com/dapitch666/ZombieDeck/actions/workflows/unit_tests.yaml)
[![Release APK](https://github.com/dapitch666/ZombieDeck/actions/workflows/release_apk.yaml/badge.svg)](https://github.com/dapitch666/ZombieDeck/actions/workflows/release_apk.yaml)

## Publish APK to GitHub

- Create these GitHub Actions secrets before publishing:
  - `ANDROID_KEYSTORE_BASE64`
  - `KEYSTORE_PASSWORD`
  - `KEY_ALIAS`
  - `KEY_PASSWORD`
- Push a tag that starts with `v` (example: `v1.0.0`).
- The workflow builds a signed release APK and publishes it in GitHub Releases.
