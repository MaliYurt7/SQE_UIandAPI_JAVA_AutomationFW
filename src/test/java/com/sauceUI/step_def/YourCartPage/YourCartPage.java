package com.sauceUI.step_def.YourCartPage;

import com.sauceUI.step_def.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YourCartPage extends BasePage {

    @FindBy(id = "checkout")
    public WebElement getCheckoutBtn;


    @FindBy(css = ".inventory_item_name")
    public List<WebElement> getProductNumbers;



}
