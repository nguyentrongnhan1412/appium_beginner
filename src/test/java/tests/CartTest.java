package tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import data.TestAccount;
import models.Account;
import models.Product;
import pages.CartPage;
import pages.CatalogPage;
import pages.LoginPage;
import pages.ProductDetailsPage;
import providers.ProductsProvider;

public class CartTest extends BaseTest{
    private CatalogPage catalogPage;
    private LoginPage loginPage;
    private ProductDetailsPage productDetailsPage;

    private CartPage cartPage;

    @BeforeMethod(alwaysRun = true)
    public void Setup() {
        loginPage = new LoginPage();
        catalogPage = new CatalogPage();
        productDetailsPage = new ProductDetailsPage();
        cartPage = new CartPage();
    }

    @Test(dataProvider = "products", dataProviderClass = ProductsProvider.class)
    public void testAddProductsIntoCart(List<Product> products) {

        Account testAccount = TestAccount.get("TEST");
        catalogPage.appBar.openSideMenu();
        catalogPage.goToLoginPage();
        loginPage.login(testAccount.getUsername(), testAccount.getPassword());

        for (Product product : products) {
            catalogPage.goToCatalogProductDetail(product.getProductName());
            productDetailsPage.AddProductToCartWithDefinedAmountAndReturn(product.getProductAmount());
        }
        catalogPage.appBar.openCartPage();

        List<Product> actualProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++)
        {
            Product actualProduct = cartPage.getProduct(i);
            actualProducts.add(actualProduct);
        }

        assertThat(actualProducts).isEqualTo(products);
    }
}
