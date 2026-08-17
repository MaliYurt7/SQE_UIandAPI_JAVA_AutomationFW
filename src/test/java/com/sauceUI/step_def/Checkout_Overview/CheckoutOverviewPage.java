package com.sauceUI.step_def.Checkout_Overview;

import com.sauceUI.step_def.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutOverviewPage extends BasePage {


    @FindBy(css = ".summary_subtotal_label")
    public WebElement getItemTotalPrice;


    @FindBy(id = "finish")
    public WebElement getFinishBtn;


}
