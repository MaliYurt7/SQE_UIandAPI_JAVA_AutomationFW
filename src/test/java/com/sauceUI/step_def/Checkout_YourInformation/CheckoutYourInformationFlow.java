package com.sauceUI.step_def.Checkout_YourInformation;

import com.sauceUI.utilities.BrowserUtils;

import java.util.Map;

public class CheckoutYourInformationFlow {
    CheckoutYourInformationPage checkoutYourInformationPage = new CheckoutYourInformationPage();

    public void fillCheckoutForm(Map<String, String> details) {
        checkoutYourInformationPage.getFirstNameInputSection.sendKeys(details.get("FirstName"));
        checkoutYourInformationPage.getLastNameInputSection.sendKeys(details.get("LastName"));
        checkoutYourInformationPage.getPostCodeInputSection.sendKeys(details.get("Zip/PostalCode"));

    }

}
