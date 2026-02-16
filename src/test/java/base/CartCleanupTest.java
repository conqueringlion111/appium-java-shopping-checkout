package base;

import org.testng.annotations.AfterMethod;
import pages.AppNavigator;

public abstract class CartCleanupTest extends BaseTest {

    @AfterMethod(alwaysRun = true)
    public void cleanupCart() {
        // Only does work if cart has items (your reset method already checks badge)
        new AppNavigator(driver).resetToCatalogCleanState();
    }

}
