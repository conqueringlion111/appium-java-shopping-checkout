# appium-java-shopping-checkout

[![Java](https://img.shields.io/badge/Java-21-informational)](#)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-informational)](#)
[![Appium](https://img.shields.io/badge/Appium-3.x-informational)](#)
[![Platform](https://img.shields.io/badge/Platform-Android-success)](#)
[![Build](https://github.com/YOUR_GITHUB_USERNAME/appium-java-shopping-checkout/actions/workflows/tests.yml/badge.svg)](https://github.com/YOUR_GITHUB_USERNAME/appium-java-shopping-checkout/actions/workflows/tests.yml)

End-to-end mobile UI automation framework using **Appium + Java + TestNG**.  
Includes **shopping / cart / checkout** style tests against **Sauce Labs My Demo App** on:
- ✅ Android Emulator
- ✅ Real Android device (USB debugging)

> iOS support is planned (framework structured to extend to XCUITest later).

---

## What’s inside

### Framework features
- Page Object Model (POM) with explicit waits
- DriverFactory / BaseTest lifecycle
- Works on emulator or real device using simple CLI properties
- Locator strategy: `accessibilityId` → `resource-id` → UiAutomator (XPath last)

### Test coverage (initial)
- Catalog loads + open product details
- Add item to cart + verify item in cart
- (Next) Checkout validation + required field errors
- (Next) Remove item from cart + verify totals update

---

## Prerequisites (Android)

- Java **21**
- Android SDK + platform tools (`adb`)
- Appium Server (v3.x recommended)
- An Android emulator AVD OR a real Android phone with USB debugging enabled

Verify:
```bash
java -version
adb version
appium -v
adb devices
