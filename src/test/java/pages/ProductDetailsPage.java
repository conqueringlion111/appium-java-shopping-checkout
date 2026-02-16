package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ProductDetailsPage extends BasePage {

    private final By addToCartBtnId = By.id("com.saucelabs.mydemoapp.android:id/cartBt");
    private final By addToCartBtnA11y = AppiumBy.accessibilityId("Tap to add product to cart");

    public ProductDetailsPage(AndroidDriver driver) {
        super(driver);
    }

    private final By cartIcon = By.id("com.saucelabs.mydemoapp.android:id/cartIV");

    public ProductDetailsPage addToCart() {
        // Prefer ID; fallback to accessibility id
        clickEither(addToCartBtnId, addToCartBtnA11y);
        return this;
    }

    public CartPage openCartViaIcon() {
        By cartIcon = By.id("com.saucelabs.mydemoapp.android:id/cartIV");
        click(cartIcon);
        return new CartPage(driver);
    }

}
