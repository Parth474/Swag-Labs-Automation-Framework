package listner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import basetest.BaseTest;
import utils.ScreenShotUtil;

public class Listner implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

        // Create fresh screenshot folder
        try {
            String suiteName = context.getSuite().getName();
            File screenshotsDir = new File("test-output/screenshots/" + suiteName);
            if (screenshotsDir.exists()) {
                FileUtils.deleteDirectory(screenshotsDir);
            }
            screenshotsDir.mkdirs();
            System.out.println("Old screenshots deleted. Fresh folder created.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize Extent
        ExtentSparkReporter spark =
                new ExtentSparkReporter("test-output/ExtentReport.html");

        spark.config().setDocumentTitle("Swag Lab Test Execution Report");
        spark.config().setReportName("Extent Report");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());

        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail(result.getThrowable());

        Object obj = result.getInstance();
        WebDriver driver = ((BaseTest) obj).driver;

        String testName = result.getMethod().getMethodName();
        String path = ScreenShotUtil.takeScreenShot(driver, testName);

        try {
            test.get().addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}