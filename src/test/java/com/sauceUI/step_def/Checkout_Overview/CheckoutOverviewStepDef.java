package com.sauceUI.step_def.Checkout_Overview;

import io.cucumber.java.en.Then;

public class CheckoutOverviewStepDef {

    CheckoutOverviewFlow checkoutOverviewFlow = new CheckoutOverviewFlow();

    @Then("the total price should  Item total: ${double}")
    public void the_total_price_should_item_total_$(Double itemTotalPrice) {

        checkoutOverviewFlow.validateItemTotal(itemTotalPrice);

    }
}
