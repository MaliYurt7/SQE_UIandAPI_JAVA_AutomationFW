package com.sauceUI.step_def.Checkout_Complete;

import com.sauceUI.step_def.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(css = ".complete-header")
    public WebElement getThankYouMsg;
    @FindBy(id = "back-to-products")
    public WebElement getBackHomeBtn;


}
