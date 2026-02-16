package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class DriverFactory {

    private DriverFactory() {

    }

    public static AndroidDriver createAndroidDriver() throws Exception {
        String serverUrl = System.getProperty("appiumUrl", "http://127.0.0.1:4723");

        // Emulator default, override for real phone: -Dudid=YOUR_DEVICE_UDID
        String udid = System.getProperty("udid", "emulator-5554");

        String appPackage = System.getProperty("appPackage");
        if (appPackage == null || appPackage.isBlank()) {
            appPackage = "com.saucelabs.mydemoapp.android";
        }

        String appActivity = System.getProperty("appActivity");
        if (appActivity == null || appActivity.isBlank()) {
            appActivity = "com.saucelabs.mydemoapp.android.view.activities.MainActivity";
        }

        // Optional APK path override
        String appPathProp = System.getProperty("appPath", "");

        UiAutomator2Options opts = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setUdid(udid)
                .setDeviceName("Android Device") // not used when udid is set, but harmless
                .setNoReset(true)
                .setNewCommandTimeout(Duration.ofSeconds(120));

        if (!appPathProp.isBlank()) {
            String abs = Paths.get(appPathProp).toAbsolutePath().toString();
            opts.setApp(abs);
        } else if (!appPackage.isBlank() && !appActivity.isBlank()) {
            opts.setAppPackage(appPackage);
            opts.setAppActivity(appActivity);
            opts.setAppWaitActivity("*"); // helpful if app redirects on startup
        } else {
            throw new IllegalArgumentException(
                    "Provide either -DappPath=... OR -DappPackage=... -DappActivity=..."
            );
        }

        return new AndroidDriver(new URL(serverUrl), opts);
    }

}
