package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Page for Appium Android tests.
 * - Explicit waits only (no implicit waits)
 * - Simple click/type helpers
 * - Useful UiAutomator scroll helpers
 */
public abstract class BasePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(AndroidDriver driver) {
        this.driver = driver;
        int timeout = Integer.parseInt(System.getProperty("waitSec", "20"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    /* ---------- Wait helpers ---------- */

    protected WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement clickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected boolean isPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }

    protected boolean isVisible(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /* ---------- Interaction helpers ---------- */

    protected void click(By by) {
        clickable(by).click();
    }

    protected void type(By by, String text) {
        WebElement el = visible(by);
        el.clear();
        el.sendKeys(text);
    }

    protected String textOf(By by) {
        WebElement el = visible(by);
        String attr = el.getAttribute("text"); // Android often stores visible text here
        return (attr != null && !attr.isBlank()) ? attr : el.getText();
    }

    /* ---------- Locator fallbacks ---------- */

    /**
     * Click by primary locator; if not present, click by fallback.
     */
    protected void clickEither(By primary, By fallback) {
        if (isPresent(primary)) click(primary);
        else click(fallback);
    }

    /**
     * Returns the first visible element among candidates (in order).
     */
    protected WebElement firstVisible(By... candidates) {
        for (By by : candidates) {
            if (isPresent(by)) {
                try {
                    WebElement el = driver.findElement(by);
                    if (el.isDisplayed()) return el;
                } catch (Exception ignored) {}
            }
        }
        throw new NoSuchElementException("No visible elements found for provided candidates.");
    }

    /* ---------- UiAutomator helpers ---------- */

    /**
     * Scroll a scrollable container until text is visible (exact match).
     */
    protected By uiScrollIntoViewByText(String exactText) {
        String uiScroll =
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"" + exactText + "\"))";
        return AppiumBy.androidUIAutomator(uiScroll);
    }

    /**
     * Scroll and tap element with exact text.
     */
    protected void scrollToTextAndTap(String exactText) {
        click(uiScrollIntoViewByText(exactText));
    }

    /**
     * Quick "contains text" finder (no scroll). Useful for assertions.
     */
    protected By uiTextContains(String partialText) {
        return AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + partialText + "\")");
    }

    /* ---------- Optional: small debug helpers ---------- */

    protected String currentActivitySafe() {
        try {
            return driver.currentActivity();
        } catch (Exception e) {
            return "(unknown activity)";
        }
    }

    protected int intFromText(By by, int defaultValue) {
        try {
            if (!isPresent(by)) return defaultValue;
            String t = driver.findElement(by).getText();
            if (t == null || t.isBlank()) return defaultValue;
            return Integer.parseInt(t.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
