package com.sauceUI.step_def.Checkout_YourInformation;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;

import java.util.Map;

public class CheckoutYourInformationStepDef {

    CheckoutYourInformationFlow checkoutYourInformationFlow = new CheckoutYourInformationFlow();

    @Then("user enters the following details")
    public void user_enters_the_following_details(DataTable dataTable) {
        Map<String, String> details = dataTable.asMap(String.class, String.class);

        checkoutYourInformationFlow.fillCheckoutForm(details);


    }
}
