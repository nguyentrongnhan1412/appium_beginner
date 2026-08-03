package core.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import core.driver.DriverManager;
import io.appium.java_client.AppiumDriver;

public final class WaitUtils {
    private static final int DEFAULT_TIMEOUT = ConfigurationUtils.getInt("timeout.default");
    private WaitUtils() {}

    private static FluentWait<AppiumDriver> getWait(int timeoutSeconds) {

        return new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }


    public static WebElement waitForVisible(By locator) {

        return waitForVisible(locator, DEFAULT_TIMEOUT);
    }


    public static WebElement waitForVisible(
            By locator,
            int timeoutSeconds) {

        return getWait(timeoutSeconds)
                .until(driver -> {

                    WebElement element = driver.findElement(locator);

                    return element.isDisplayed()
                            ? element
                            : null;
                });
    }

    public static WebElement waitForVisibleAfterScroll(By locator, int timeout) {
        AtomicReference<String> direction = new AtomicReference<>("down");

        return getWait(timeout)
                .until(driver -> {

                    List<WebElement> elements = driver.findElements(locator);

                    if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                        return elements.get(0);
                    }

                    if (!scroll(driver, direction.get())) {
                        direction.set(
                                "down".equals(direction.get()) ? "up" : "down"
                        );
                        scroll(driver, direction.get());
                    }

                    return null;
                });
    }

    public static WebElement waitForEnabledAfterScroll(By locator, int timeout) {
        AtomicReference<String> direction = new AtomicReference<>("down");

        return getWait(timeout)
                .until(driver -> {

                    List<WebElement> elements = driver.findElements(locator);

                    if (!elements.isEmpty() && elements.get(0).isEnabled()) {
                        return elements.get(0);
                    }

                    if (!scroll(driver, direction.get())) {
                        direction.set(
                                "down".equals(direction.get()) ? "up" : "down"
                        );
                        scroll(driver, direction.get());
                    }

                    return null;
                });
    }

    /**
     * Scrolls within the current viewport. Returns {@code true} when content moved.
     */
    private static boolean scroll(AppiumDriver driver, String direction) {
        Dimension size = driver.manage().window().getSize();

        Map<String, Object> params = new HashMap<>();
        params.put("left", (int) (size.width * 0.1));
        params.put("top", (int) (size.height * 0.2));
        params.put("width", (int) (size.width * 0.8));
        params.put("height", (int) (size.height * 0.6));
        params.put("direction", direction);
        params.put("percent", 0.7);

        return Boolean.TRUE.equals(
                driver.executeScript("mobile: scrollGesture", params)
        );
    }


    public static WebElement waitForEnabled(By locator) {

        return waitForEnabled(locator, DEFAULT_TIMEOUT);
    }


    public static WebElement waitForEnabled(
            By locator,
            int timeoutSeconds) {

        return getWait(timeoutSeconds)
                .until(driver -> {

                    WebElement element = driver.findElement(locator);

                    return element.isEnabled()
                            ? element
                            : null;
                });
    }


    public static boolean waitForInvisible(By locator) {

        return getWait(DEFAULT_TIMEOUT)
                .until(driver -> {

                    List<WebElement> elements =
                            driver.findElements(locator);

                    return elements.isEmpty()
                            || !elements.get(0).isDisplayed();
                });
    }


    public static List<WebElement> findElements(By locator) {

        return DriverManager.getDriver()
                .findElements(locator);
    }


    public static WebElement findElement(By locator) {

        return DriverManager.getDriver()
                .findElement(locator);
    }
}
