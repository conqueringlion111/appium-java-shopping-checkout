package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AppNavigator;
import pages.CartPage;
import pages.CatalogPage;

public class ScrollToElementTest extends BaseTest {

    @Test
    public void addBikeLightToCart() {
        String productName = "Sauce Labs Onesie";

        new AppNavigator(driver).resetToCatalogCleanState();
        new CatalogPage(driver)
                .waitForLoaded()
                .scrollToElement(productName);

    }
}
