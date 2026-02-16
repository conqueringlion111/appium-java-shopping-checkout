package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    private final By toPaymentBtnId = By.id("com.saucelabs.mydemoapp.android:id/paymentBtn");
    private final By toPaymentBtnA11y = AppiumBy.accessibilityId("Saves user info for checkout");

    // Generic “required” error finder (works well for validation UIs)
    private final By requiredErrorAny = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Please provide your\")");

    public CheckoutPage(AndroidDriver driver) {
        super(driver);
    }

    public CheckoutPage waitForLoaded() {
        // On this screen, To Payment should be visible/clickable
        clickable(toPaymentBtnId);
        return this;
    }

    public void submitEmptyUserInfo() {
        waitForLoaded();
        clickEither(toPaymentBtnId, toPaymentBtnA11y);
    }

    public boolean hasRequiredFieldErrors() {
        return driver.findElements(requiredErrorAny).size() > 0;
    }

}
