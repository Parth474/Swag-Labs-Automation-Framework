package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	WebDriver driver;
	WebDriverWait wait;
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	// Locators
	By username = By.id("user-name");
	By password = By.id("password");
	By loginBtn = By.id("login-button");
	By error = By.xpath("//h3[@data-test='error']");
	By catalog = By.xpath("//*[contains(text(),'Products')]");
	
	// Methods
	public void fillUserData(String userN, String pass) {
		driver.findElement(username).sendKeys(userN);
		driver.findElement(password).sendKeys(pass);
		clickLogin();
	}
	public void fillUser(String userN) {
		driver.findElement(username).sendKeys(userN);
	}
	
	public void fillPass(String pass) {
		driver.findElement(password).sendKeys(pass);
	}
	
	public void clickLogin() {
		driver.findElement(loginBtn).click();
	}
	
	public boolean productCatalog() {
		return driver.findElements(catalog).size()>0;
	}
	
	public boolean errorDisp() {
		return driver.findElements(error).size()>0;
	}
	
	public void errMsg() {
		if(errorDisp()) {
			System.out.println("Error message displayed: " + driver.findElement(error).getText());
		}else {
			System.out.println("Error not displayed");
		}
	}
}
