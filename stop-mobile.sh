#!/usr/bin/env bash
set -euo pipefail

echo "[1/3] Stopping Appium..."
pkill -f "node.*appium" 2>/dev/null || true
pkill -f "appium" 2>/dev/null || true

echo "[2/3] Stopping emulator..."
adb -e emu kill 2>/dev/null || true

echo "[3/3] Remaining (if any):"
pgrep -fa "emulator|qemu|appium|node.*appium" || true
