package com.sauceUI.step_def.Checkout_Complete;

import org.testng.Assert;

public class CheckoutCompleteFlow {

    CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage();

    public void validateSuccessMsg(String expectedThankYouMsg) {

        Assert.assertEquals(checkoutCompletePage.getThankYouMsg.getText(), expectedThankYouMsg);

    }
}
