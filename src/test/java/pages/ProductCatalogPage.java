package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductCatalogPage {
	
	WebDriver driver;
	WebDriverWait wait;
	LoginPage login;

	// Locators
	
	// Info of product
	By proName = By.cssSelector(".inventory_item_name");
	By proDesc = By.cssSelector(".inventory_item_desc");
	By proPrice = By.cssSelector(".inventory_item_price");
	
	// Info from product page
	By detailTitle = By.cssSelector(".inventory_details_name");
	By detailDesc  = By.cssSelector(".inventory_details_desc");
	By detailPrice = By.cssSelector(".inventory_details_price");
	
	
	@FindBy(css="div.inventory_list div.inventory_item")
	public List<WebElement> productList;
	
	@FindBy(xpath="//button[contains(@class, 'btn_inventory')]")
	private List<WebElement> addCartBtn;
	
	@FindBy(css="span.shopping_cart_badge")
	WebElement cartCnt;
	
	@FindBy(xpath="//button[@id='react-burger-menu-btn']")
	WebElement hamBtn;
	
	@FindBy(xpath="//div[@class='bm-menu-wrap'] //button[@id='react-burger-cross-btn']")
	public WebElement clsBtn;
	
	@FindBy(id="logout_sidebar_link")
	WebElement logoutBtn;
	
	@FindBy(css="select.product_sort_container")
	public WebElement filterDropDown;
	
	
	public ProductCatalogPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	// Methods
	public boolean isOnCatalogPage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container"))).isDisplayed();
	}
	
	public void clickFirstProductName() {
		wait.until(ExpectedConditions.elementToBeClickable(proName));
		productList.get(0).findElement(proName).click();
	}
	
	public void addMultipleProductsToCart() {
		int totalProducts = productList.size();
		
		for(int i=0; i<totalProducts/2; i++) {
			productList.get(i).findElement(By.xpath("//button[contains(@id, 'add-to-cart')]")).click();
		}
	}
	public boolean proList() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
		return productList.size()>0;
	}
	
	public boolean verifyBtn() {
		int cnt = 0;
		for(WebElement btn : addCartBtn) {
			btn.click(); cnt++;
		}
		
		int actualCartCount = Integer.parseInt(cartCnt.getText());
		System.out.println("Expected count = " + cnt + " actual count = " + actualCartCount);
		return cnt == actualCartCount;
	}
	
	public boolean checkFirstProductInfo() {
		WebElement card = productList.get(0);
		
		String title = card.findElement(proName).getText();
		String desc = card.findElement(proDesc).getText();
		String price = card.findElement(proPrice).getText();
		
		System.out.println(title + " " + desc + " " + price);
		
		card.findElement(proName).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_item_container")));

		String pTitle = driver.findElement(detailTitle).getText();
	    String pDesc  = driver.findElement(detailDesc).getText();
	    String pPrice = driver.findElement(detailPrice).getText();
	 
    	driver.navigate().back();
		wait.until(ExpectedConditions.visibilityOfAllElements(productList));

		System.out.println(pPrice + " " + pTitle + " " + pDesc);
		return title.equals(pTitle) && desc.equals(pDesc) && price.equals(pPrice);
	}
	
	public void openHam() {
		wait.until(ExpectedConditions.elementToBeClickable(hamBtn));
		hamBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(clsBtn));
	}
	
	public void hamClose() {
		try {
			clsBtn.click();	
//			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".bm-menu")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bm-menu-wrap' and @aria-hidden='true']")));
		}catch(Exception e) {
			
		}
	}
	public void clickFirstProductAddToCartBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class, 'btn_inventory')])[1]")));
		productList.get(0).findElement(By.xpath("//button[contains(@class, 'btn_inventory')]")).click();
	}
	
	public boolean verifyLogoutBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
		logoutBtn.click();
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.login_wrapper"))).isDisplayed();
	}

	public boolean checkFilterResult(String opt) {

	    if (opt.equals("az") || opt.equals("za")) {

	        List<String> actual = driver.findElements(proName)
	                .stream()
	                .map(WebElement::getText)
	                .toList();

	        List<String> sorted = new ArrayList<>(actual);

	        if (opt.equals("az"))
	            Collections.sort(sorted);
	        else
	            Collections.sort(sorted, Collections.reverseOrder());

	        return actual.equals(sorted);
	    }

	    else if (opt.equals("lohi") || opt.equals("hilo")) {

	        List<Double> actual = driver.findElements(proPrice)
	                .stream()
	                .map(e -> Double.parseDouble(
	                        e.getText().replaceAll("[^0-9.]", "")))
	                .toList();

	        List<Double> sorted = new ArrayList<>(actual);

	        if (opt.equals("lohi"))
	            Collections.sort(sorted);
	        else
	            Collections.sort(sorted, Collections.reverseOrder());

	        return actual.equals(sorted);
	    }

	    return false;
	}

}
