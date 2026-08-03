package pages;

import org.openqa.selenium.By;

import core.element.Element;
import io.appium.java_client.AppiumBy;

public class LoginPage extends BasePage {
    private final Element usernameInput = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/nameET"),
            "Username Input");
    private final Element passwordInput = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/passwordET"),
            "Password Input");

    private final Element loginButton = new Element(
            AppiumBy.accessibilityId("Tap to login with given credentials"),
            "Login Button");

    private final Element usernameErrorMessage = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/nameErrorTV"),
            "Username Error Message");

    private final Element passwordErrorMessage = new Element(
            By.id("com.saucelabs.mydemoapp.android:id/passwordErrorTV"),
            "Password Error Message");

    public void login(String username, String password){
        usernameInput.enterText(username);
        passwordInput.enterText(password);
        loginButton.click();
    }

    public String getUsernameErrorMessage(){
        return usernameErrorMessage.getText();
    }

    public String getPasswordErrorMessage(){
        return passwordErrorMessage.getText();
    }
}
