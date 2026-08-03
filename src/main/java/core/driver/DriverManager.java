package core.driver;

import io.appium.java_client.AppiumDriver;

public class DriverManager {
    private DriverManager() {}
    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    public static void setDriver(AppiumDriver driver) {
        DRIVER.set(driver);
    }
    public static AppiumDriver getDriver() {

        AppiumDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "Driver is not initialized. Call setDriver() first."
            );
        }
        return driver;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {

        AppiumDriver driver = DRIVER.get();

        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }
}
