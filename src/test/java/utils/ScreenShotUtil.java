package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;


public class ScreenShotUtil {
	public static String takeScreenShot(WebDriver driver, String testName) {		
		String path = "test-output/screenshots/testngxml-screenshot/" + testName + "_" + ".png";
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File desc = new File(path);
			FileUtils.copyFile(src, desc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
