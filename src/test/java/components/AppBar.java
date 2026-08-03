package components;

import org.openqa.selenium.By;

import core.element.Element;
import io.appium.java_client.AppiumBy;

public class AppBar {
    private final Element sideMenuButton = new Element(
            AppiumBy.accessibilityId("View menu"),
            "Side Menu Button",
            false);

    private final Element cartButton = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/cartIV"),
            "Cart Button",
            false);

    public void openSideMenu() {
        sideMenuButton.click();
    }

    public void openCartPage(){
        cartButton.click();
    }
}
