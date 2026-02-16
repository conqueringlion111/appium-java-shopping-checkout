package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CatalogPage extends BasePage {

    private final By productTileImage = By.id("com.saucelabs.mydemoapp.android:id/productIV");
    private final By cartBadgeCount = By.id("com.saucelabs.mydemoapp.android:id/cartTV");

    public CatalogPage(AndroidDriver driver) {
        super(driver);
    }

    private final By productImage = By.id("com.saucelabs.mydemoapp.android:id/productIV");
    private final By cartIcon = By.id("com.saucelabs.mydemoapp.android:id/cartIV");

    public CatalogPage waitForLoaded() {
        // Wait until at least one product is visible
        visible(productImage);
        return this;
    }

    public int getCartCount() {
        return intFromText(cartBadgeCount, 0);
    }

    public ProductDetailsPage openFirstProduct() {
        waitForLoaded();
        List<WebElement> tiles = driver.findElements(productTileImage);
        if (tiles.isEmpty()) throw new RuntimeException("No products found on catalog.");
        tiles.get(0).click();
        return new ProductDetailsPage(driver);
    }

    public CartPage openCart() {
        click(cartIcon);
        return new CartPage(driver);
    }

    public boolean isLoaded() {
        return isPresent(productTileImage);
    }

    private void relaunchToMain() {
        driver.activateApp("com.saucelabs.mydemoapp.android");
        // If app is in a weird state, restart activity by terminating & activating.
        // (activateApp alone often works, but this is more deterministic.)
        driver.terminateApp("com.saucelabs.mydemoapp.android");
        driver.activateApp("com.saucelabs.mydemoapp.android");
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }

}
