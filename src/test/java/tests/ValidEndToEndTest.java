package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.CartPage;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ProductCatalogPage;
import pages.ProductDetailPage;

public class ValidEndToEndTest extends BaseTest {

    LoginPage login;
    ProductCatalogPage catalog;
    ProductDetailPage detail;
    CartPage cart;
    CheckOutPage checkout;

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        login = new LoginPage(driver);
        catalog = new ProductCatalogPage(driver);
        detail = new ProductDetailPage(driver);
        cart = new CartPage(driver);
        checkout = new CheckOutPage(driver);
    }

    // Common reusable steps
    // ---------------------------
    private void loginWithValidUser() {
        login.fillUserData("standard_user", "secret_sauce");
        Assert.assertTrue(catalog.isOnCatalogPage(), "Login failed - not on catalog page");
    }

    private void completeCheckoutFlow() {
        checkout.clickCartCheckOutBtn();
        checkout.fillCheckOutInfo("Parth", "Dangare", "444001");
        checkout.clickContinue();
        checkout.clickFinishBtn();

        Assert.assertTrue(checkout.checkSuccessMsg(), "Order was not completed successfully");
    }

    // ---------------------------
    // E2E SCENARIOS
    // ---------------------------

    @Test(groups = "e2e", priority = 1)
    public void e2e_AddFromCatalog() {

        loginWithValidUser();

        catalog.clickFirstProductAddToCartBtn();

        cart.clickCart();

        completeCheckoutFlow();

        checkout.clickBackHomeBtn();

        Assert.assertTrue(checkout.navToCatalog(), 
                "Back Home button did not navigate to catalog page");
    }

    @Test(groups = "e2e", priority = 2)
    public void e2e_AddFromProductDetailPage() {

        loginWithValidUser();

        catalog.clickFirstProductName();  // opens detail page

        Assert.assertTrue(detail.isOnDetailPage(), 
                "Not navigated to product detail page");

        detail.clickAddToCartBtn();

        cart.clickCart();

        completeCheckoutFlow();

        checkout.clickBackHomeBtn();

        Assert.assertTrue(checkout.navToCatalog(), 
                "Back Home button did not navigate to catalog page");
    }

    @Test(groups = "e2e", priority = 3)
    public void e2e_MultipleProductsCheckout() {

        loginWithValidUser();

        catalog.addMultipleProductsToCart();

        cart.clickCart();

        completeCheckoutFlow();

        checkout.clickBackHomeBtn();

        Assert.assertTrue(checkout.navToCatalog(), 
                "Back Home button did not navigate to catalog page");
    }
}
