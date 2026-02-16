package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.util.Map;

public class AppNavigator extends BasePage {

    private final By cartIcon = By.id("com.saucelabs.mydemoapp.android:id/cartIV");
    private final By cartBadge = By.id("com.saucelabs.mydemoapp.android:id/cartTV");

    private final By removeItemBtn = By.id("com.saucelabs.mydemoapp.android:id/removeBt");
    private final By hamburgerMenu = By.id("com.saucelabs.mydemoapp.android:id/menuIV");

    private final By catalogMenuItem = AppiumBy.androidUIAutomator("new UiSelector().text(\"Catalog\")");

    private final By catalogSentinel = By.id("com.saucelabs.mydemoapp.android:id/productIV");

    // App identifiers
    private static final String APP_PKG = "com.saucelabs.mydemoapp.android";
    private static final String MAIN_INTENT =
            "com.saucelabs.mydemoapp.android/com.saucelabs.mydemoapp.android.view.activities.MainActivity";

    public AppNavigator(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Puts the app into a known baseline:
     * - Launch main activity
     * - If cart badge > 0: open cart and click Remove Item (clears cart)
     * - Open hamburger menu and tap Catalog
     * - Wait until Catalog is loaded
     */
    public void resetToCatalogCleanState() {
        startMainActivity();

        int count = intFromText(cartBadge, 0);
        if (count > 0) {
            openCartAndClear();
        }

        openMenuAndGoToCatalog();

        // final proof
        visible(catalogSentinel);
    }

    private void startMainActivity() {
        try {
            driver.executeScript("mobile: startActivity", Map.of("intent", MAIN_INTENT));
        } catch (Exception e) {
            // fallback if mobile: startActivity isn't supported by your setup
            driver.activateApp(APP_PKG);
        }
        sleep(300);
    }

    private void openCartAndClear() {
        int before = intFromText(cartBadge, 0);

        click(cartIcon);

        // Wait until we can see the cart screen (Remove button or cart button etc.)
        // If remove is present, click it once (your app clears cart in one click)
        if (isPresent(removeItemBtn)) {
            click(removeItemBtn);

            // PROOF: wait until cart count drops to 0 (or badge disappears)
            wait.until(d -> intFromText(cartBadge, 0) == 0);
        } else {
            // If there were items, but we didn't find remove, that's a sign we aren't on cart screen yet
            // Wait a moment for cart screen to load then try once more
            visible(removeItemBtn);
            click(removeItemBtn);
            wait.until(d -> intFromText(cartBadge, 0) == 0);
        }

        // Optional: If badge still not 0, try one more time (rare under heavy load)
        int after = intFromText(cartBadge, 0);
        if (before > 0 && after > 0 && isPresent(removeItemBtn)) {
            click(removeItemBtn);
            wait.until(d -> intFromText(cartBadge, 0) == 0);
        }

        // Now back out
        driver.navigate().back();
        // Wait until the hamburger is visible again (meaning we’re back to main app navigation)
        visible(hamburgerMenu);
    }

    private void openMenuAndGoToCatalog() {
        click(hamburgerMenu);
        click(catalogMenuItem);
        sleep(200);
    }

    public void emptyCartIfNeeded() {
        startMainActivity();
        int count = intFromText(cartBadge, 0);
        if (count > 0) openCartAndClear();
        // optionally return to catalog
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
}
