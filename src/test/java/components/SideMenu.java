package components;

import org.openqa.selenium.By;

import core.element.Element;
import io.appium.java_client.AppiumBy;

public class SideMenu {

    private final Element catalogOption = new Element(
            By.xpath(
                    "//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='Catalog']"),
            "Catalog Option");
    private final Element loginOption = new Element(
            AppiumBy.accessibilityId("Login Menu Item"),
            "Login Option"
    );

    private final Element logoutOption = new Element(
            AppiumBy.accessibilityId("Logout Menu Item"),
            "Logout Option"
    );

    public void selectCatalogOption(){
        catalogOption.click();
    }

    public void selectLoginOption(){
        loginOption.click();
    }

    public boolean isLogoutOptionDisplayed() {
        try {
            return logoutOption.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public void selectLogoutOption() {logoutOption.click();}
}
