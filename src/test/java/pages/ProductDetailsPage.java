package pages;

import org.openqa.selenium.By;

import core.element.Element;
import io.appium.java_client.AppiumBy;

public class ProductDetailsPage extends BasePage{
    private final Element productName = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/productTV"),
            "Product Name");

    private final Element productPrice = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/priceTV"),
            "Product Price"
    );

    private final Element productAmount = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/noTV"),
            "Product Amount"
    );

    private final Element increaseAmountButton = new Element(
            AppiumBy.accessibilityId("Increase item quantity"),
            "Increase Amount Button"
    );

    private final Element decreaseAmountButton = new Element(
            AppiumBy.accessibilityId("Decrease item quantity"),
            "Decrease Amount Button");

    private final Element addToCartButton = new Element(
            AppiumBy.accessibilityId("Tap to add product to cart"),
            "Add To Cart Button"
    );

    public void AddProductToCartWithDefinedAmountAndReturn(int amount){
        for (int i = 1; i<amount; i++){
            increaseAmountButton.click();
        }
        addToCartButton.click();
        appBar.openSideMenu();
        sideMenu.selectCatalogOption();
    }
    public String getProductPrice()
    {
        return productPrice.getText();
    }
}
