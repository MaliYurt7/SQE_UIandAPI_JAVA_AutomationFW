package com.sauceUI.step_def.Checkout_YourInformation;

import com.sauceUI.step_def.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutYourInformationPage extends BasePage {


    @FindBy(id = "first-name")
    public WebElement getFirstNameInputSection;
    @FindBy(id = "last-name")
    public WebElement getLastNameInputSection;
    @FindBy(id = "postal-code")
    public WebElement getPostCodeInputSection;

    @FindBy(id = "continue")
    public WebElement getContinueBtn;


}
