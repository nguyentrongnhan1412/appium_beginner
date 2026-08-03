# Mobile UI Test Automation

Appium + TestNG framework for the Sauce Labs My Demo App on Android and iOS.

## Stack

| Layer | Technology |
| --- | --- |
| Language | Java 17 |
| Build | Gradle |
| Automation | Appium Java Client 10 |
| Test runner | TestNG |
| Assertions | AssertJ |
| Reporting | Allure |
| Appium server | Node.js (`appium` + UiAutomator2 / XCUITest drivers) |

## Project structure

```
src/main/java/core/     Driver factory, platform adapters, waits, config, Allure listener
src/main/resources/     Environment config (config.properties, config-qa.properties)
src/test/java/
  tests/                TestNG test classes
  pages/                Page objects
  components/           Shared UI components (app bar, side menu, …)
  models/               DTOs
  providers/            TestNG data providers
  data/                 Typed loaders for JSON fixtures
src/test/resources/
  testng.xml            Suite definition
  data/                 JSON test data (accounts, credentials, products)
```

## Prerequisites

- JDK 17+
- Node.js 18+ (for Appium)
- Android SDK / emulator (Android) or Xcode / simulator (iOS)
- My Demo App installed on the target device, or an `.apk` / `.ipa` path set in config

## Setup

```bash
# Install Appium and drivers
npm install

# Ensure Appium can see the drivers (first-time / CI)
npx appium driver install uiautomator2
npx appium driver install xcuitest
```

Start the Appium server before running tests:

```bash
npx appium
```

Server URL used by the framework: `http://127.0.0.1:4723`

## Configuration

Defaults live in `src/main/resources/config.properties`. Optional overrides:

| Mechanism | Example |
| --- | --- |
| Env file | `config-qa.properties` via `-Denv=qa` |
| JVM system properties | `-Dplatform=android -DdeviceName="Pixel_7"` |

Key properties (set locally; do not commit secrets or machine-specific IDs):

| Key | Description |
| --- | --- |
| `platform` | `android` or `ios` |
| `deviceName` | Emulator / simulator / device name |
| `platformVersion` | OS version |
| `udid` | Device UDID (optional) |
| `app` | Path to app package (optional if already installed) |
| `appPackage` / `appActivity` | Android launch target |
| `noReset` / `fullReset` | Session reset behavior |
| `timeout.default` | Default wait timeout (seconds) |

`local.properties` (Android SDK path, etc.) is gitignored — keep it local.

## Test data

JSON fixtures under `src/test/resources/data/` drive data providers and account lookups:

- `accounts.json` — named accounts used by tests (credentials left empty in git)
- `credentials.json` — login validation scenarios
- `products.json` — cart product scenarios

### Account credentials

`TestAccount` resolves the `TEST` account in this order:

1. JVM system properties: `-Dtest.account.username` / `-Dtest.account.password`
2. Environment variables: `TEST_ACCOUNT_USERNAME` / `TEST_ACCOUNT_PASSWORD`
3. Values in `accounts.json` (local-only fallback)

Do not commit real passwords. For local runs with the Sauce Labs demo app:

```powershell
$env:TEST_ACCOUNT_USERNAME = "bod@example.com"
$env:TEST_ACCOUNT_PASSWORD = "10203040"
.\gradlew.bat test
```

## Running tests

```bash
# Default (config.properties)
./gradlew test

# QA overrides
./gradlew test -Denv=qa

# Override platform / device at runtime
./gradlew test -Dplatform=android -DdeviceName="Android Emulator"
```

On Windows PowerShell:

```powershell
.\gradlew.bat test
```

Suite entry point: `src/test/resources/testng.xml` (`LoginTest`, `CartTest`).

## CI (GitHub Actions)

Workflow: `.github/workflows/android-tests.yml` (uses the **QA** GitHub Environment).

Add these secrets under **Settings → Environments → QA → Environment secrets**:

| Secret | Example (demo app) |
| --- | --- |
| `TEST_ACCOUNT_USERNAME` | `bod@example.com` |
| `TEST_ACCOUNT_PASSWORD` | `10203040` |

The job maps those secrets to `TEST_ACCOUNT_USERNAME` / `TEST_ACCOUNT_PASSWORD`, boots an Android emulator, installs the My Demo App APK, starts Appium, and runs `./gradlew test`.

## Reports

Allure results are written to `build/allure-results`. An HTML report is generated after `test`:

```bash
./gradlew test
# Open the path printed by the allureReport task, typically:
# build/reports/allure-report/allureReport/index.html
```

Or serve with the CLI:

```bash
npx allure serve build/allure-results
```

## Writing tests

1. Extend `BaseTest` (creates/quits the Appium session per method).
2. Use page objects under `pages/` and shared components under `components/`.
3. Prefer JSON + data providers for parameterized cases.
4. Assert with AssertJ; failures attach screenshots via `AllureListener`.

## Notes

- Android uses UiAutomator2; iOS uses XCUITest.
- Keep device UDIDs, absolute app paths, and personal SDK paths out of committed config.
- APK/AAB artifacts and Allure output directories are gitignored.
