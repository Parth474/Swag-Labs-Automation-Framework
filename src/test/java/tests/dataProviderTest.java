package tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.LoginPage;
import utils.ExcelUtil;

public class dataProviderTest {

    @Test
    public void loginTestWithExcelData() throws IOException {

        String excelPath = System.getProperty("user.dir") + "/src/test/resources/testData/LoginData.xlsx";

        WebDriver d = new ChromeDriver();

        LoginPage login = new LoginPage(d);

        int rows = ExcelUtil.getRows(excelPath, "testdata");

        for (int i = 1; i <=rows; i++) {
        	d.get("https://www.saucedemo.com/");
        	d.manage().window().maximize();
            String username = ExcelUtil.getCellData(excelPath, "testdata", i, 0);
            String password = ExcelUtil.getCellData(excelPath, "testdata", i, 1);

            login.fillUser(username);
            login.fillPass(password);
            login.clickLogin();

            if (login.productCatalog()) {
                System.out.println("Test passed " + username + " " + password);
            } else {
                System.out.println("Test failed " + username + " " + password);
            }
        }
        d.quit();
    }
}