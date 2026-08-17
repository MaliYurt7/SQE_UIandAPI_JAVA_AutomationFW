package com.sauceUI.step_def.Checkout_Complete;

import io.cucumber.java.en.Then;

public class CheckoutCompleteStepDef {

    CheckoutCompleteFlow checkoutCompleteFlow = new CheckoutCompleteFlow();

    @Then("user should see the {string} message")
    public void user_should_see_the_message(String getThankYouMsg) {
        checkoutCompleteFlow.validateSuccessMsg(getThankYouMsg);

    }
}
