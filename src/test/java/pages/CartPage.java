package pages;

import org.openqa.selenium.By;

import core.element.Element;
import models.Product;

public class CartPage extends BasePage {
    public Element cartProductName(int index) {
        return new Element(
                By.xpath("(//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/titleTV'])[" + (index + 1) + "]"),
                "Cart Product Name");
    }

    public Element cartProductPrice(int index) {
        return new Element(
                By.xpath("(//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/priceTV'])[" + (index + 1) + "]"),
                "Cart Product Price");
    }

    public Element cartProductAmount(int index) {
        return new Element(
                By.xpath("(//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/noTV'])[" + (index + 1) + "]"),
                "Cart Product Amount");
    }

    public Product getProduct(int index){
        String productName = cartProductName(index).getText();
        String productPrice = cartProductPrice(index).getText();
        int productAmount = Integer.parseInt(cartProductAmount(index).getText());
        return new Product(productName, productAmount, productPrice);
    }
}
