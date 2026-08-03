package tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constants.ErrorMessage;
import data.TestAccount;
import models.Account;
import models.Credentials;
import pages.CatalogPage;
import pages.LoginPage;
import providers.CredentialsProvider;

public class LoginTest extends BaseTest {

    private CatalogPage catalogPage;
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void Setup() {
        catalogPage = new CatalogPage();
        loginPage = new LoginPage();
    }

    @Test
    public void testLoginSuccessfullyWithValidCredentials() {
        Account testAccount = TestAccount.get("TEST");
        catalogPage.appBar.openSideMenu();
        catalogPage.goToLoginPage();

        loginPage.login(testAccount.getUsername(), testAccount.getPassword());

        catalogPage.appBar.openSideMenu();
        assertThat(catalogPage.sideMenu.isLogoutOptionDisplayed())
                .as("Logout option is visible after successful login")
                .isTrue();

    }

    @Test(dataProvider = "credentials", dataProviderClass = CredentialsProvider.class)
    public void testLoginWithEmptyUsername(Credentials credentials) {
        catalogPage.appBar.openSideMenu();
        catalogPage.goToLoginPage();

        loginPage.login(credentials.getUsername(), credentials.getPassword());
        assertThat(loginPage.getUsernameErrorMessage()).isEqualTo(ErrorMessage.MISSING_USERNAME.getValue());
    }

    @Test(dataProvider = "credentials", dataProviderClass = CredentialsProvider.class)
    public void testLoginWithEmptyPassword(Credentials credentials) {
        catalogPage.appBar.openSideMenu();
        catalogPage.goToLoginPage();

        loginPage.login(credentials.getUsername(), credentials.getPassword());
        assertThat(loginPage.getPasswordErrorMessage()).isEqualTo(ErrorMessage.MISSING_PASSWORD.getValue());
    }






}
