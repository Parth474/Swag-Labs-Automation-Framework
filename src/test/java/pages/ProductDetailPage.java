//package pages;
//
//import java.time.Duration;
//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class ProductDetailPage {
//	WebDriver driver;
//	WebDriverWait wait;
//	public ProductDetailPage(WebDriver driver){
//		this.driver = driver;
//		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		PageFactory.initElements(driver, this);
//	}
//	
////	By productList = By.className("inventory_item");
//	@FindBy(css="div.inventory_item")
//	public List<WebElement> productCards;
//	
//	@FindBy(css="button[name='back-to-products']")
//	WebElement backBtn;
//	
//	@FindBy(id="add-to-cart")
//	WebElement addToCartBtn;
//	
//	@FindBy(id="remove")
//	WebElement removeBtn;
//	
//	@FindBy(css="span.shopping_cart_badge")
//	WebElement cartBadge;
//	
//	By productTitle = By.className("inventory_item_name");
//	By productDesc = By.className("inventory_item_desc");
//	By productPrice = By.className("inventory_item_price");
//	By productImage = By.className("img.inventory_item_img");
//	
//	@FindBy(css="div.inventory_details_container")
//	WebElement productDetailCard;
//
//	By productPageImage = By.cssSelector("img.inventory_details_img");
//	By productPageTitle = By.cssSelector("div.inventory_details_name");
//	By productPageDesc = By.cssSelector("div.inventory_details_desc");
//	By productPagePrice = By.cssSelector("div.inventory_details_price");
//	
//	public void openFirstProduct() {
//	    driver.findElements(By.cssSelector("div.inventory_item"))
//	          .get(0)
//	          .findElement(productTitle)
//	          .click();
//	}
//	
//	public boolean verifyProduct(WebElement productCard) {
//		String imgUrl = productCard.findElement(By.cssSelector("img")).getAttribute("src");
//		String name = productCard.findElement(productTitle).getText();
//		String desc = productCard.findElement(productDesc).getText();
//		String price = (productCard.findElement(productPrice).getText()).replace("$", "");
//		
//		productCard.findElement(productTitle).click();
//		
//		wait.until(ExpectedConditions.visibilityOf(productDetailCard));
//		
//		String detailImgUrl = productDetailCard.findElement(productPageImage).getAttribute("src");
//		String detailName = productDetailCard.findElement(productPageTitle).getText();
//		String detailDesc = productDetailCard.findElement(productPageDesc).getText();
//		String detailPrice = (productDetailCard.findElement(productPagePrice).getText()).replace("$", "");
//		
//		return imgUrl.equals(detailImgUrl) && name.equals(detailName) && desc.equals(detailDesc) && price.equals(detailPrice);
//	}
//	
//	public boolean verifyBackNavigation(WebElement card) {
//		String proTitle = card.findElement(productTitle).getText();
//		
//		card.findElement(productTitle).click();
//		
//		wait.until(ExpectedConditions.elementToBeClickable(backBtn)).click();
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.inventory_item")));
//		
//		if(driver.findElement(By.xpath("//*[text()='" + proTitle+ "']")).isDisplayed()) {
//			return true;
//		}
//		return false;
//	}
//	
//	public int getBadgeNumber() {
//		List<WebElement> badge = driver.findElements(By.cssSelector("span.shopping_cart_badge"));
//		
//		int currBadgeTotal;
//		
//		if(badge.size()>0) {
//			currBadgeTotal = Integer.parseInt(cartBadge.getText());
//		}else {
//			currBadgeTotal = 0;
//		}
//		return currBadgeTotal;
//	}
//	
//	public void addProductToCart() {
//	    addToCartBtn.click();
//	}
//
//	public void removeProductFromCart() {
//	    removeBtn.click();
//	}
//
//	
//	public boolean verifyCart() {
////		card.findElement(productTitle).click();
//		openFirstProduct();
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart")));
//		
//		int currBadgeTotal = getBadgeNumber();
//		
//		System.out.println(currBadgeTotal);
//		
//		addProductToCart();
//		
//		int updatedBadgeTotal = Integer.parseInt(cartBadge.getText());
//		System.out.println(updatedBadgeTotal);
//		return (currBadgeTotal+1) == updatedBadgeTotal;
//	}
//	
//	public boolean VerifyRemoveCart() {
//		List<WebElement> badge =
//	            driver.findElements(By.cssSelector("span.shopping_cart_badge"));
//		
////		card.findElement(productTitle).click();
//		openFirstProduct();
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart")));
//		
//		int currBadgeTotal = getBadgeNumber();
//		
//		System.out.println(currBadgeTotal);
//		
//		addProductToCart();
//		
//		int updatedBadgeTotal = Integer.parseInt(cartBadge.getText());
//		
////		wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
//		removeProductFromCart();
//		
//		System.out.println(updatedBadgeTotal);
//		updatedBadgeTotal = getBadgeNumber();
//		System.out.println(updatedBadgeTotal);
//		
//		return currBadgeTotal == updatedBadgeTotal;
//	}
//	
//	
//}

package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class ProductDetailPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ---------- LOCATORS ----------

    private By productCards = By.cssSelector("div.inventory_item");
    private By productTitle = By.className("inventory_item_name");
    private By cartBadge = By.cssSelector("span.shopping_cart_badge");
    private By productDetailContainer =
            By.cssSelector("div.inventory_details_container");
    By detailPage = By.cssSelector("div.inventory_details_container");
	
    @FindBy(css = "button[id^='add-to-cart']")
    private WebElement addToCartBtn;

    @FindBy(css = "button[id^='remove']")
    private WebElement removeBtn;

    @FindBy(id = "back-to-products")
    private WebElement backBtn;

    // ---------- PRODUCT ACTIONS ----------

    public void openFirstProduct() {
        driver.findElements(productCards)
              .get(0)
              .findElement(productTitle)
              .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailContainer));
    }

    public boolean verifyProductDetails(int index) {

        List<WebElement> cards = driver.findElements(productCards);
        WebElement card = cards.get(index);

        String name = card.findElement(productTitle).getText();

        card.findElement(productTitle).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(productDetailContainer));

        String detailName =
                driver.findElement(By.className("inventory_details_name"))
                      .getText();

        return name.equals(detailName);
    }
    
    public boolean isOnDetailPage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(detailPage)).isDisplayed();
	}

    public boolean verifyBackNavigation(int index) {

        List<WebElement> cards = driver.findElements(productCards);
        String name = cards.get(index)
                           .findElement(productTitle)
                           .getText();

        cards.get(index).findElement(productTitle).click();

        wait.until(ExpectedConditions
                .elementToBeClickable(backBtn)).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(productCards));

        return driver.findElement(
                By.xpath("//*[text()='" + name + "']"))
                .isDisplayed();
    }

    // ---------- BADGE ----------

    public int getBadgeNumber() {
        List<WebElement> badges = driver.findElements(cartBadge);

        if (!badges.isEmpty()) {
            return Integer.parseInt(badges.get(0).getText());
        }
        return 0;
    }
    
    public void clickAddToCartBtn() {
    	addToCartBtn.click();
    }
    public boolean verifyAddToCart() {

        openFirstProduct();

        int before = getBadgeNumber();

        addToCartBtn.click();

        wait.until(driver -> getBadgeNumber() == before + 1);

        return getBadgeNumber() == before + 1;
    }

    public boolean verifyRemoveFromCart() {

        openFirstProduct();

        int before = getBadgeNumber();

        addToCartBtn.click();

        wait.until(driver -> getBadgeNumber() == before + 1);

        removeBtn.click();

        wait.until(driver -> getBadgeNumber() == before);

        return getBadgeNumber() == before;
    }
}

