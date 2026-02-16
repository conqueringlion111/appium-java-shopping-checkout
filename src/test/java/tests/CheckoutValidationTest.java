package tests;

import base.BaseTest;
import base.CartCleanupTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutValidationTest extends CartCleanupTest {

    @Test
    public void requiresUserInfoBeforePayment() {

        CatalogPage catalog = new CatalogPage(driver).waitForLoaded();
        int before = catalog.getCartCount();

        CartPage cart = catalog
                .openFirstProduct()
                .addToCart()
                .openCartViaIcon();

        int after = cart.getCartCount();
        Assert.assertTrue(after >= before + 1,
                "Cart badge did not increment. Before=" + before + " After=" + after);

        cart.proceedToCheckout();

        // Login screen may appear. Handle it if present.
        LoginPage login = new LoginPage(driver);
        if (login.isLoaded()) {
            login.chooseUsername1().tapLogin();
        }

        CheckoutPage checkout = new CheckoutPage(driver).waitForLoaded();
        checkout.submitEmptyUserInfo();

        Assert.assertTrue(checkout.hasRequiredFieldErrors(),
                "Expected required field validation errors on Checkout screen.");
    }

}
