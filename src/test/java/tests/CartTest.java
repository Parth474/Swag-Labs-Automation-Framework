package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.CartPage;
import pages.LoginPage;

public class CartTest extends BaseTest {
	CartPage cart;
	LoginPage login;
	WebDriverWait wait;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("user")
	public void openBrowswer(String username) {
//		driver.get("https://www.saucedemo.com/");
		cart = new CartPage(driver);
		login = new LoginPage(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		login.fillUser(username);
		login.fillPass("secret_sauce");
		login.clickLogin();
	}
	
	@Test(priority=1, groups= {"regression"})
	public void verifyCartItemData() {
		Assert.assertTrue(cart.validateItemDetails());
	}
	
	@Test(priority=2, groups= {"regression"})
	public void verifyContinueShopping() {
		Assert.assertTrue(cart.checkContinueShoppingBtn());
	}
	
	@Test(priority=3, groups= {"regression"})
	public void verifyCheckoutWithEmptyCart() {
		cart.clickCheckOutBtn();
		WebElement checkOutInfoBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.checkout_info")));
		Assert.assertFalse(checkOutInfoBlock.isDisplayed());
	}
	
	@Test(priority=4, groups= {"regression"})
	public void verifyCheckOutWithProduct() {
		cart.addProducttoCart();
		cart.clickCheckOutBtn();
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.checkout_info"))).isDisplayed());
	}
	
	@Test(priority=5, groups= {"regression"})
	public void verifyCartPersist() {
		cart.addProducttoCart();
		cart.clickCart();
		driver.navigate().refresh();
		
		Assert.assertTrue(driver.findElements(cart.cartList).size()>0);
	}
	
	@Test(priority=6, groups= {"regression"})
	public void verifyRemoveItem() {
		cart.addProducttoCart();
		cart.clickCart();
		
		wait.until(ExpectedConditions.elementToBeClickable(cart.continueShoppingBtn));
		
		int currBadge = cart.getCartBadge();
		cart.clickRemove();
		
		int updatedBadge = cart.getCartBadge();
		
		Assert.assertTrue((currBadge-1) == updatedBadge);
	}
}
