package com.sauceUI.step_def.YourCartPage;

import com.sauceUI.step_def.BasePage;
import org.testng.Assert;

public class YourCartFlow {

    YourCartPage yourCartPage =new YourCartPage();

    public void getNumberOfProdcut(Integer expectedProductNumber){
        System.out.println("yourCartPage.getProductNumbers.size() = " + yourCartPage.getProductNumbers.size());
        Assert.assertEquals(yourCartPage.getProductNumbers.size(),expectedProductNumber);
    }
}
