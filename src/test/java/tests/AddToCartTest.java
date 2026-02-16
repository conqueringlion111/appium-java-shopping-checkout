package tests;

import base.BaseTest;
import base.CartCleanupTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AppNavigator;
import pages.CatalogPage;
import pages.CartPage;

public class AddToCartTest extends CartCleanupTest {

    @Test
    public void addBackpackToCart() {
        String productName = "Sauce Labs Backpack";

        new AppNavigator(driver).resetToCatalogCleanState();
        CatalogPage catalog = new CatalogPage(driver).waitForLoaded();
        CartPage cart = new CatalogPage(driver)
                .waitForLoaded()
                .openFirstProduct()
                .addToCart()
                .openCartViaIcon();

        Assert.assertTrue(cart.hasProductNamed(productName),
                "Cart does not contain expected product: " + productName);
    }

}
