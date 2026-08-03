package core.listeners;

import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import core.driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;

public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        if (DriverManager.hasDriver()) {
            attachScreenshot();
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attachScreenshot() {
        AppiumDriver driver = DriverManager.getDriver();
        return driver.getScreenshotAs(OutputType.BYTES);
    }
}
