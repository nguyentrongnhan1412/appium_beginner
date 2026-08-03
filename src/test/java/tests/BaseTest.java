package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import core.driver.DriverFactory;
import core.driver.DriverManager;
import core.utilities.ConfigurationUtils;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void beforeEach() {
        String platform = ConfigurationUtils.get("platform");
        DriverManager.setDriver(
                DriverFactory.createDriver(platform, buildCapabilities())
        );
    }

    @AfterMethod(alwaysRun = true)
    public void afterEach() {
        DriverManager.quitDriver();
    }

    private Map<String, Object> buildCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();

        putIfPresent(capabilities, "appium:deviceName", "deviceName");
        putIfPresent(capabilities, "appium:platformVersion", "platformVersion");
        putIfPresent(capabilities, "appium:udid", "udid");
        putIfPresent(capabilities, "appium:app", "app");
        putIfPresent(capabilities, "appium:appPackage", "appPackage");
        putIfPresent(capabilities, "appium:appActivity", "appActivity");
        putIfPresent(capabilities, "appium:appWaitActivity", "appWaitActivity");

        String appWaitDuration = ConfigurationUtils.get("appWaitDuration");
        if (appWaitDuration != null && !appWaitDuration.isBlank()) {
            capabilities.put("appium:appWaitDuration", Integer.parseInt(appWaitDuration.trim()));
        }

        capabilities.put(
                "appium:noReset",
                ConfigurationUtils.getBoolean("noReset")
        );
        capabilities.put(
                "appium:fullReset",
                ConfigurationUtils.getBoolean("fullReset")
        );

        return capabilities;
    }

    private void putIfPresent(
            Map<String, Object> capabilities,
            String capabilityName,
            String configKey) {

        String value = ConfigurationUtils.get(configKey);

        if (value != null && !value.isBlank()) {
            capabilities.put(capabilityName, value);
        }
    }
}
