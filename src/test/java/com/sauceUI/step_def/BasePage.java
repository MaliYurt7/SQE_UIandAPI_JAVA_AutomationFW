package com.sauceUI.step_def;

import com.sauceUI.utilities.BrowserUtils;
import com.sauceUI.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    public BasePage() {
        PageFactory.initElements(Driver.get(), this);
    }

    public String pageNavigation(String module) {
        String moduleLocator = "//span[text()='" + module + "']";
        BrowserUtils.waitForVisibility(Driver.get().findElement(By.xpath(moduleLocator)), 5);
        return Driver.get().findElement(By.xpath(moduleLocator)).getText();

    }
}
