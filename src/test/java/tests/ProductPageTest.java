package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.LoginPage;
import pages.ProductDetailPage;

public class ProductPageTest extends BaseTest{
	WebDriverWait wait;
	ProductDetailPage detail;
	LoginPage login;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("user")
	public void openWeb(String username) {
		detail = new ProductDetailPage(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		login = new LoginPage(driver);
		
		login.fillUser(username);
		login.fillPass("secret_sauce");
		login.clickLogin();
	}
	
	@Test(priority = 1, groups = {"regression", "product"})
	public void verifyFirstProductDetails() {
		System.out.println("Hello" + " verifyFirstProductDetails" );
	    Assert.assertTrue(detail.verifyProductDetails(0));
	}

	@Test(priority = 2, groups = {"regression", "product"})
	public void verifyLastProductDetails() {
	    Assert.assertTrue(detail.verifyProductDetails(
	            driver.findElements(By.cssSelector("div.inventory_item")).size() - 1));
	}

	@Test(priority = 3, groups = {"smoke", "regression", "navigation"})
	public void verifyBackButton() {
	    Assert.assertTrue(detail.verifyBackNavigation(0));
	}

	@Test(priority = 4, groups = {"regression", "cart"})
	public void verifyAddToCart() {
	    Assert.assertTrue(detail.verifyAddToCart());
	}

	@Test(priority = 5, groups = {"regression", "cart"})
	public void verifyRemoveFromCart() {
	    Assert.assertTrue(detail.verifyRemoveFromCart());
	}
}
