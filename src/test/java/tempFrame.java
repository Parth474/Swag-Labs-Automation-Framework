import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class tempFrame {
	WebDriver driver;
	@Test
		public void te() {
		driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://ui.vision/demo/webtest/frames/");
		driver.manage().window().maximize();
		
//		WebElement fram3 = driver.findElement(By.xpath("//frame[@src='frame_3.html']"));
////		WebElement insideFrame3 = driver.findElement(By.xpath();
//		driver.switchTo().frame(fram3);
//		driver.switchTo().frame(0);
//		
//		driver.findElement(By.xpath("//div[@class='eBFwI']//div[@id='i21']")).click();
//		driver.switchTo().defaultContent();
//		System.out.println("Frame 3 is validated");
		
		// Frame 5
		WebElement frame5 = driver.findElement(By.xpath("//frame[@src='frame_5.html']"));
		driver.switchTo().frame(frame5);
		driver.findElement(By.xpath("//a[@href='https://a9t9.com']")).click();
		
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a#logo"))).isDisplayed());
		driver.switchTo().defaultContent();
		System.out.println("Logo is present!! & frame 5 is validated");
		
		driver.quit();
	}
}
