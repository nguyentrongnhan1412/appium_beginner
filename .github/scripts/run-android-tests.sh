#!/usr/bin/env bash
set -euo pipefail

adb wait-for-device
adb install -r apps/mda.apk

setsid npx appium --address 127.0.0.1 --port 4723 --log appium.log &
APPIUM_PID=$!

cleanup() {
  if kill -0 "$APPIUM_PID" 2>/dev/null; then
    kill -- "-$APPIUM_PID" 2>/dev/null || kill "$APPIUM_PID" 2>/dev/null || true
    sleep 2
    kill -9 -- "-$APPIUM_PID" 2>/dev/null || kill -9 "$APPIUM_PID" 2>/dev/null || true
  fi
  adb forward --remove-all 2>/dev/null || true
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
