package tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import core.driver.DriverManager;
import core.utilities.ConfigurationUtils;

public class Hooks {

    @BeforeSuite(alwaysRun = true)
    public void beforeAll() {
        requireConfig("platform");
        requireConfig("timeout.default");
    }

    @AfterSuite(alwaysRun = true)
    public void afterAll() {
        DriverManager.quitDriver();
    }

    private void requireConfig(String key) {
        String value = ConfigurationUtils.get(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required config key: " + key
            );
        }
    }
}
