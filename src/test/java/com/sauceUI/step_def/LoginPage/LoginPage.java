package com.sauceUI.step_def.LoginPage;

import com.sauceUI.step_def.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {


    @FindBy(id = "user-name")
    public WebElement usernameInputBox;

    @FindBy(id = "password")
    public WebElement passwordInputBox;

    @FindBy(id = "login-button")
    public WebElement loginButton;


    @FindBy(css = "[data-test='error']")
    public WebElement loginErrorMsg;


}
