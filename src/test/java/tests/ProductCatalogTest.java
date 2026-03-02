package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.LoginPage;
import pages.ProductCatalogPage;

public class ProductCatalogTest extends BaseTest {
	ProductCatalogPage catalog;
	LoginPage login;
	private WebDriverWait wait;
	
	@BeforeMethod
	@Parameters("user")
	public void openWeb(@Optional("standard_user")  String username) {
//		driver.get("https://www.saucedemo.com/");
		login = new LoginPage(driver);
		login.fillUser(username);
		login.fillPass("secret_sauce");
		login.clickLogin();
		
		catalog = new ProductCatalogPage(driver);
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	
	@Test
	public void checkProductVisible() {
	    Assert.assertTrue(catalog.proList(), "Product isn't visible");
	}

	@Test
	public void verifyCartBtn() {
	    Assert.assertTrue(catalog.verifyBtn(), "Cart button isn't working properly");
	}

	@Test
	public void verifyInfo() {
	    Assert.assertTrue(catalog.checkFirstProductInfo(), "Product details aren't matching");
	}

	@Test
	public void verifyHamOpen() {
	    catalog.openHam();
	    Assert.assertTrue(catalog.clsBtn.isDisplayed(), "Hamburger open not working");
	}

	@Test
	public void verifyHamClose() {
	    catalog.openHam();
	    catalog.hamClose();
	    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='bm-menu-wrap' and @aria-hidden='true']")).isDisplayed(), "Hamburger close not working");
	}

	@Test
	public void verifyLogout() {
	    catalog.openHam();
	    Assert.assertTrue(catalog.verifyLogoutBtn(), "Hamburger logout not working");
	}

	@Test
	public void verifyFilter() {

	    Select s = new Select(driver.findElement(By.cssSelector("select.product_sort_container")));

	    // Step 1: Extract values first (safe)
	    List<String> values = s.getOptions()
	            .stream()
	            .map(e -> e.getAttribute("value"))
	            .toList();

	    // Step 2: Iterate using values
	    for (String value : values) {

	        // Reinitialize select every time (important)
	        Select select = new Select(driver.findElement(By.cssSelector("select.product_sort_container")));
	        select.selectByValue(value);

	        Assert.assertTrue(
	                catalog.checkFilterResult(value),
	                "Sorting failed for " + value
	        );
	    }
	}



}
