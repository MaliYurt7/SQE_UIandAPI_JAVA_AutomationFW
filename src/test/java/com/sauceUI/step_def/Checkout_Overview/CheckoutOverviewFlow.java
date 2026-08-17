package com.sauceUI.step_def.Checkout_Overview;

import io.cucumber.java.en.Then;
import org.testng.Assert;

public class CheckoutOverviewFlow {

    CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage();

    public void validateItemTotal(Double expectedItemTotalPrice) {

        String getItemTotalText = checkoutOverviewPage.getItemTotalPrice.getText();
        double actualItemTotalPrice = Double.parseDouble(getItemTotalText.split("\\$")[1]);

        Assert.assertEquals(actualItemTotalPrice, expectedItemTotalPrice);

    }


}
