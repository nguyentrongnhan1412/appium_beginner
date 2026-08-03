package pages;

import org.openqa.selenium.By;

import core.element.Element;

public class CatalogPage extends BasePage {
    public void goToLoginPage() {
        sideMenu.selectLoginOption();
    }

    public Element catalogProduct(String productName) {
        return new Element(
                By.xpath("//android.widget.TextView[@content-desc='Product Title' and @text='" + productName + "']/preceding-sibling::android.widget.ImageView[@content-desc='Product Image']"),
                "Catalog Product"
        );
    }

    public void goToCatalogProductDetail(String productName)
    {
        Element targetedProduct = catalogProduct(productName);
        targetedProduct.click();
    }
}
