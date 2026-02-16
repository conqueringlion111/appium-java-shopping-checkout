package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class CartPage extends BasePage {

    private final By cartBadgeCount = By.id("com.saucelabs.mydemoapp.android:id/cartTV");

    // Proceed to checkout:
    private final By proceedCheckoutId = By.id("com.saucelabs.mydemoapp.android:id/cartBt");
    private final By proceedCheckoutA11y = AppiumBy.accessibilityId("Confirms products for checkout");

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Simple and stable assertion: check the cart page contains the product name text.
     * (Works well for demo apps and avoids guessing internal item ids.)
     */
    public boolean hasProductNamed(String productName) {
        return driver.findElements(
                AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + productName + "\")")
        ).size() > 0;
    }

    public int getCartCount() {
        return intFromText(cartBadgeCount, 0);
    }

    public CartPage waitForLoaded() {
        // Basic load check: cart button should be clickable on this screen
        clickable(proceedCheckoutId);
        return this;
    }

    public void proceedToCheckout() {
        waitForLoaded();
        clickEither(proceedCheckoutId, proceedCheckoutA11y);
    }

}
