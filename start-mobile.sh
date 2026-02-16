#!/usr/bin/env bash
set -euo pipefail

AVD_NAME="${AVD_NAME:-Medium_Phone_API_36.1}"
APPIUM_HOST="${APPIUM_HOST:-127.0.0.1}"
APPIUM_PORT="${APPIUM_PORT:-4723}"
EMULATOR_LOG="${EMULATOR_LOG:-/tmp/emulator.log}"
APPIUM_LOG="${APPIUM_LOG:-/tmp/appium.log}"

echo "[1/4] Starting emulator (if not already running): ${AVD_NAME}"

# If no emulator device is listed, start one
if ! adb devices | awk '{print $1}' | grep -q '^emulator-'; then
  nohup emulator -avd "${AVD_NAME}" -netdelay none -netspeed full > "${EMULATOR_LOG}" 2>&1 &
else
  echo "  Emulator already running."
fi

echo "[2/4] Waiting for device..."
adb wait-for-device

# Wait for boot completion
BOOT_OK=""
for i in {1..60}; do
  BOOT_OK="$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')"
  if [[ "${BOOT_OK}" == "1" ]]; then
    echo "  Emulator booted."
    break
  fi
  sleep 1
done

if [[ "${BOOT_OK}" != "1" ]]; then
  echo "ERROR: Emulator did not finish booting in time. Check ${EMULATOR_LOG}"
  exit 1
fi

echo "[3/4] Starting Appium (if not already running) on ${APPIUM_HOST}:${APPIUM_PORT}"

# If Appium already listening on port, don't start another
if ss -ltn 2>/dev/null | awk '{print $4}' | grep -q ":${APPIUM_PORT}\$"; then
  echo "  Appium already running on port ${APPIUM_PORT}."
else
  nohup appium --address "${APPIUM_HOST}" --port "${APPIUM_PORT}" > "${APPIUM_LOG}" 2>&1 &
  sleep 1
fi

echo "[4/4] Status"
adb devices || true

echo
echo "Logs:"
echo "  Emulator: ${EMULATOR_LOG}"
echo "  Appium:   ${APPIUM_LOG}"
