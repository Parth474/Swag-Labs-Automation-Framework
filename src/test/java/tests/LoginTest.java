package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import dataprovider.loginDP;
import pages.LoginPage;
import utils.ExcelUtil;

public class LoginTest extends BaseTest{
	LoginPage login;
	String excelPath = System.getProperty("user.dir") + "/src/test/resources/testData/LoginData.xlsx";
	@BeforeMethod(alwaysRun=true)
	public void setupPage() {
		login = new LoginPage(driver);
	}
	
	private void fillData(String user, String pass) {
		login.fillUser(user);
		login.fillPass(pass);
	}

	@Test(priority=1, groups = {"smoke", "regression", "sanity", "functional"})
	@Parameters("user")
	public void validLogin(String username){
		try {
			logger.info("validLogin test started...");
			logger.info("Entering username and password");
			fillData(username, "secret_sauce");
			logger.info("clicking login button");
			login.clickLogin();
			Assert.assertTrue(login.productCatalog(), "Login failed with valid data");
		}catch(Exception e) {
			logger.debug("Debug started....");
			logger.error("validLogin failed....");
		}
	}
	
//	@Test(dataProvider = "getLoginData", dataProviderClass = loginDP.class)
//	public void loginTest(String username, String password, String result, int row) {
//		login.fillUser(username);
//	    login.fillPass(password);
//	    login.clickLogin();
//	    
////	    Assert.assertTrue(
////	            login.productCatalog() || login.errorDisp(),
////	            "Unexpected result for user: " + username
////	        );
//	    String actualResult = null;
//	    if(login.errorDisp() && !login.productCatalog()) {
//	    	actualResult = "Fail";
//	    }else if(login.productCatalog() && !login.errorDisp()) {
//	    	actualResult = "Pass";
//	    }
//	    
//	    ExcelUtil.updateResult(excelPath, "testdata", row, 3, actualResult);
//	}
	
	@Test(priority=4, groups={"regression"})
	public void lockedUser() {
		fillData("locked_out_user", "secret_sauce");
		login.clickLogin();
		
		Assert.assertFalse(!login.errorDisp(), "Login passed with locked user");
		login.errMsg();
	}
	
	@Test(priority=5, groups={"regression"})
	public void refreshLogin() {
		fillData("locked_out_user", "secret_sauce");
		driver.navigate().refresh();
		
		Assert.assertTrue(driver.findElement(By.id("user-name")).getAttribute("value").equalsIgnoreCase("") &&
				driver.findElement(By.id("password")).getText().equalsIgnoreCase(""), 
				"Username and Password is not empty after refresh"
		);
		login.errMsg();
	}
	
	@Test(priority=6, groups={"regression"})
	public void passwordMask() {
		String type = driver.findElement(By.id("password")).getAttribute("type");
		System.out.println(type);
		Assert.assertTrue(type.equalsIgnoreCase("password"), "Password is not masked");
		login.errMsg();
	}
}
