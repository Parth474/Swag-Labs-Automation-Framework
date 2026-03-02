package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOutPage {
	WebDriver driver;
	WebDriverWait wait;
	CartPage cart;
	
	public CheckOutPage(WebDriver driver) {
		this.driver = driver;
		cart = new CartPage(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@FindBy(id="shopping_cart_container")
	WebElement cartBtn;
	
	@FindBy(css="div.cart_footer button#checkout")
	WebElement checkoutBtn;
	
	@FindBy(css="span.shopping_cart_badge")
	List<WebElement> cartBadges;
	
	// Info page
	
	@FindBy(id="first-name")
	WebElement firstName;
	
	@FindBy(id="last-name")
	WebElement lastName;
	
	@FindBy(id="postal-code")
	WebElement postalCode;
	
	@FindBy(css="div.error h3")
	WebElement errorMsg;
	
	@FindBy(css="div.checkout_buttons button#cancel")
	WebElement infoCancelBtn;
	
	@FindBy(css="div.checkout_buttons input#continue")
	WebElement continueBtn;
	
	// Overview Page
	@FindBy(css="div.cart_item_label")
	public List<WebElement> cartItems;
	
	By checkoutProTitle = By.cssSelector("div.cart_item_label div.inventory_item_name");
	By checkoutProDesc = By.cssSelector("div.cart_item_label div.inventory_item_desc");
	By checkoutProPrice = By.cssSelector("div.cart_item_label div.inventory_item_price");

	public By catalogProTitle = By.cssSelector("div.inventory_item_label div.inventory_item_name");
	public By catalogProDesc = By.cssSelector("div.inventory_item_label div.inventory_item_desc");
	public By catalogProPrice = By.cssSelector("div.pricebar div.inventory_item_price");
	
	@FindBy(css="div.summary_subtotal_label")
	WebElement subTotal;
	
	@FindBy(css="div.summary_tax_label")
	WebElement totalTax;
	
	@FindBy(css="div.summary_total_label")
	WebElement total;
	
	@FindBy(css="div.cart_footer button#cancel")
	WebElement cartCancelBtn;
	
	@FindBy(css="div.cart_footer button#finish")
	WebElement cartFinishBtn;
	
	// Complete 
	@FindBy(css="button#back-to-products")
	WebElement backHomeBtn;
	
	@FindBy(xpath="//h2[@class='complete-header' and contains(text(), 'Thank you for your order')]")
	WebElement orderSuccessMsg;
	
	@FindBy(css="div.inventory_item")
	public List<WebElement> catalogItems;
	
	public String getTitle() {
		return cart.getProductTitle();
	}
	
	public String getDesc() {
		return cart.getProductDesc();
	}
	
	public String getPrice() {
		return cart.getProductPrice();
	}
	
	public void addMultipleProductsToCart() {
		for(int i=0; i<catalogItems.size()/2; i++) {
			catalogItems.get(i).findElement(By.xpath(".//button[text()='Add to cart']")).click();
		}
	}
	
//	public boolean checkProductInfo(String t, String d, String p) {
//		String title = t;
//		String desc = d;
//		String price = p;
//		
//		WebElement card = cartItems.get(0);
//		String cardTitle = card.findElement(checkoutProTitle).getText();
//		String cardDesc = card.findElement(checkoutProDesc).getText();
//		String cardPrice = (card.findElement(checkoutProPrice).getText()).replace("$", "");
//		
//		return title.equals(cardTitle) && desc.equals(cardDesc) && price.equals(cardPrice);
//	}
	
	public void addProductToCart() {
		cart.addProducttoCart();
	}
	public void clickContinue() {
		continueBtn.click();
	}
	public void clickCart() {
		cartBtn.click();
	}
	public void clickCartCheckOutBtn() {
		checkoutBtn.click();
	}
	
	public void clickOverviewCancelBtn() {
		cartCancelBtn.click();
	}
	public void clickFinishBtn() {
		cartFinishBtn.click();
	}
	
	public boolean checkSuccessMsg() {
		return orderSuccessMsg.isDisplayed();
	}
	public boolean checkErrDisplayed() {
		return errorMsg.isDisplayed();
	}
	
	public void clickInfoCancelBtn() {
		infoCancelBtn.click();
	}
	
	public void fillCheckOutInfo(String fName, String lName, String code) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.checkout_info")));
		firstName.sendKeys(fName);
		lastName.sendKeys(lName);
		postalCode.sendKeys(code);
	}
	
	public boolean navToOverviewPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.summary_info")));
		return driver.findElement(By.cssSelector("div.summary_info")).isDisplayed();
	}
	
	public boolean navToCart() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Your Cart')]")));
		return driver.findElement(By.xpath("//span[contains(text(),'Your Cart')]")).isDisplayed();
	}
	
	public boolean navToCatalog() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container"))).isDisplayed();
	}
	
	public int getBadgeCnt() {
		int totalItemsInCart = cartItems.size();
		int badgeCnt = 0;
		if(cartBadges.size()>0) {
			badgeCnt = Integer.parseInt(cartBadges.get(0).getText());
		}else {
			badgeCnt = 0;
		}
		return badgeCnt;
		
	}
	public Double getSubTotal() {
		wait.until(ExpectedConditions.visibilityOf(subTotal));
		return Double.parseDouble(subTotal.getText().replaceAll("[^0-9.]", ""));
	}
	
	public Double getTotalTax() {
		return Double.parseDouble(totalTax.getText().replaceAll("[^0-9.]", ""));
	}
	
	public Double getTotal() {
		return Double.parseDouble(total.getText().replaceAll("[^0-9.]", ""));
	}

	public void clickBackHomeBtn() {
		backHomeBtn.click();
	}
	
}
