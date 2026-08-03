package core.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import core.utilities.ConfigurationUtils;
import core.utilities.WaitUtils;

public class Element {
    private final By by;
    private final String name;
    private final int timeout;
    private final boolean scrollToFind;

    public Element(By by, String name) {
        this(by, name, ConfigurationUtils.getInt("timeout.default"), true);
    }

    public Element(By by, String name, boolean scrollToFind) {
        this(by, name, ConfigurationUtils.getInt("timeout.default"), scrollToFind);
    }

    public Element(By by, String name, int timeout) {
        this(by, name, timeout, true);
    }

    public Element(By by, String name, int timeout, boolean scrollToFind) {
        this.by = by;
        this.name = name;
        this.timeout = timeout;
        this.scrollToFind = scrollToFind;
    }

    public By getBy() {
        return by;
    }

    public String getName() {
        return name;
    }

    public int getTimeout() {
        return timeout;
    }

    public WebElement waitForVisible() {
        if (scrollToFind) {
            return WaitUtils.waitForVisibleAfterScroll(by, timeout);
        }
        return WaitUtils.waitForVisible(by, timeout);
    }

    public WebElement waitForEnabled() {
        return WaitUtils.waitForEnabled(by, timeout);
    }

    public boolean isDisplayed() {
        WaitUtils.waitForVisible(by, timeout);
        return true;
    }

    public void click() {

        System.out.println("Click on " + name);

        WebElement element = waitForVisible();

        if (!element.isEnabled()) {
            throw new RuntimeException(
                    "Element is disabled: " + name
            );
        }

        element.click();
    }


    public void enterText(String value) {

        System.out.println(
                "Enter [" + value + "] into " + name
        );

        WebElement element = waitForVisible();

        element.clear();
        element.sendKeys(value);
    }


    public String getText() {

        System.out.println(
                "Get text from " + name
        );

        return waitForVisible()
                .getText();
    }


    public String getAttribute(String attribute) {

        return waitForVisible()
                .getAttribute(attribute);
    }


    public List<String> getTexts() {

        return WaitUtils.findElements(by)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }


    public WebElement find() {

        return WaitUtils.findElement(by);
    }


    public List<WebElement> findAll() {

        return WaitUtils.findElements(by);
    }
}
