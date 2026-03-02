package tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
import pages.CheckOutPage;
import pages.LoginPage;

public class CheckOutTest extends BaseTest {
	
	CheckOutPage check;
	CartPage cart;
	LoginPage login;
	WebDriverWait wait;
	
	@BeforeMethod(alwaysRun=true)
//	@Parameters("user")
	public void openBrowser() {
//		driver.get("https://www.saucedemo.com/");
		check = new CheckOutPage(driver);
		cart = new CartPage(driver);
		login = new LoginPage(driver);

		login.fillUser("standard_user");
		login.fillPass("secret_sauce");
		login.clickLogin();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	private void infoFlowWithMultipleProducts() {
		check.addMultipleProductsToCart();
		check.clickCart();
		check.clickCartCheckOutBtn();
	}
	
	private void infoFlow() {
		check.addProductToCart();
		check.clickCart();
		check.clickCartCheckOutBtn();
	}
	
	@Test(priority=1)
	public void verifyCancelBtn() {
		infoFlow();
		check.clickInfoCancelBtn();
		
		Assert.assertTrue(check.navToCart());
	}
	
	@Test(priority=2)
	public void verifyContinueWithoutInfo() {
		infoFlow();
		check.clickContinue();
		
		Assert.assertTrue(check.checkErrDisplayed());
		
	}
	
	@Test(priority=3, groups= {"smoke"})
	public void verifyContinueWithInfo() {
		infoFlow();
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		Assert.assertTrue(check.navToOverviewPage());
	}
	
	@Test(priority=4, groups={"regression"}) // failed
	public void verifyProductDetailOnOverview() {
		List<String> proNames = new ArrayList<>();
		List<String> proDescs = new ArrayList<>();
		List<String> proPrices = new ArrayList<>();
		
		for(int i=0; i<check.catalogItems.size()/2; i++) {
			
			proNames.add(check.catalogItems.get(i).findElement(By.cssSelector("div.inventory_item_label div.inventory_item_name")).getText());
			proDescs.add(check.catalogItems.get(i).findElement(By.cssSelector("div.inventory_item_label div.inventory_item_desc")).getText());
			proPrices.add(check.catalogItems.get(i).findElement(By.cssSelector("div.pricebar div.inventory_item_price")).getText().replace("$", ""));
			
			check.catalogItems.get(i).findElement(By.xpath(".//button[text()='Add to cart']")).click();
		}
		
		check.clickCart();
		check.clickCartCheckOutBtn();
		
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.summary_info")));
		
		List<String> checkOutItemTitles = new ArrayList<>();
		List<String> checkOutItemDescs = new ArrayList<>();
		List<String> checkOutItemPrices = new ArrayList<>();
		List<WebElement> checkoutItems = driver.findElements(By.cssSelector("div.cart_item_label"));
		
		for(int i=0; i< checkoutItems.size(); i++) {
			checkOutItemTitles.add(checkoutItems.get(i).findElement(By.cssSelector("div.inventory_item_name")).getText()); 
			checkOutItemDescs.add(checkoutItems.get(i).findElement(By.cssSelector("div.inventory_item_desc")).getText()); 
			checkOutItemPrices.add(checkoutItems.get(i).findElement(By.cssSelector("div.inventory_item_price")).getText().replace("$", "")); 
		}	
		Assert.assertEquals(proNames, checkOutItemTitles);
		Assert.assertEquals(proDescs, checkOutItemDescs);
		Assert.assertEquals(proPrices, checkOutItemPrices);
	}
	
	@Test(priority=5, groups={"regression"}) // failed
	public void verifyItemTotal() {
		infoFlowWithMultipleProducts();
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		
		double totalItemPrice = 0;
		for(int i=0; i<check.cartItems.size(); i++) { 
			WebElement item = check.cartItems.get(i);
			totalItemPrice += Double.parseDouble(item.findElement(By.cssSelector("div.inventory_item_price")).getText().replace("$", ""));
		}
		double sub_Total = check.getSubTotal();
		System.out.println(sub_Total + " " + totalItemPrice);
		Assert.assertEquals(totalItemPrice, sub_Total);
	}
	
	@Test(priority=6, groups={"regression"}) // failed
	public void verifyCartTotal() {
		infoFlowWithMultipleProducts();
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		
		double sub_Total = check.getSubTotal();
		double total_tax = check.getTotalTax();
		double total = check.getTotal();
		System.out.println(sub_Total + " " + total_tax + " " + total);
		Assert.assertTrue((sub_Total + total_tax) == total);
	}
	
	@Test(priority=7, groups={"regression"})
	public void verifyOverviewCancelBtn() {
		infoFlow();
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		check.clickOverviewCancelBtn();
		
		Assert.assertTrue(check.navToCatalog());
	}
	
	@Test(priority=8, groups={"regression"})
	public void verifyCartBadge() {
		infoFlowWithMultipleProducts();
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cart_list")));
		
		int badgeCnt = check.getBadgeCnt();
		int totalItemsInCart = check.cartItems.size();
		
		System.out.println(badgeCnt + " " + totalItemsInCart);
		Assert.assertTrue(totalItemsInCart == badgeCnt);
	}
	
	@Test(priority=9, groups = {"smoke", "regression"})
	public void verifyFinishBtn() {
		infoFlow();
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		
		check.clickFinishBtn();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='complete-header' and contains(text(), 'Thank you for your order')]")));
		
		Assert.assertTrue(check.checkSuccessMsg());
	}
	
	@Test(priority=10, groups={"regression"})
	public void verifyBackHomeBtn() throws InterruptedException {
		infoFlow();
		check.fillCheckOutInfo("Parth", "Dangare", "444001");
		check.clickContinue();
		check.clickFinishBtn();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='complete-header' and contains(text(), 'Thank you for your order')]")));
		check.clickBackHomeBtn();
		Assert.assertTrue(check.navToCatalog());
	}
}
