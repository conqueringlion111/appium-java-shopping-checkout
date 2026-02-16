package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By username1 = By.id("com.saucelabs.mydemoapp.android:id/username1TV");

    private final By loginBtnIdGuess = By.id("com.saucelabs.mydemoapp.android:id/loginBtn");
    private final By loginBtnText = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Login\")");

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isPresent(username1);
    }

    public LoginPage chooseUsername1() {
        click(username1);
        return this;
    }

    public void tapLogin() {
        // Try id first, else click by visible text
        if (isPresent(loginBtnIdGuess)) click(loginBtnIdGuess);
        else click(loginBtnText);
    }

}
