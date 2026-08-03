#!/usr/bin/env bash
set -euo pipefail

adb wait-for-device
adb install -r apps/mda.apk

npx appium --address 127.0.0.1 --port 4723 --log appium.log &
APPIUM_PID=$!

cleanup() {
  kill "$APPIUM_PID" 2>/dev/null || true
}
trap cleanup EXIT

for i in $(seq 1 30); do
  if curl -sf http://127.0.0.1:4723/status >/dev/null; then
    echo "Appium is up"
    break
  fi
  if [ "$i" -eq 30 ]; then
    echo "Appium did not become ready in time"
    exit 1
  fi
  sleep 2
done

./gradlew test --no-daemon \
  -Dplatform=android \
  -DdeviceName="Android Emulator" \
  -DplatformVersion=14
