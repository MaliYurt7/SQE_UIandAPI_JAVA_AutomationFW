package com.sauceUI.step_def.YourCartPage;

import io.cucumber.java.en.*;

public class YourCartStepDef {

    YourCartFlow yourCartFlow = new YourCartFlow();

    @Then("{int} products should be displayed on the {string} page")
    public void products_should_be_displayed_on_the_page(Integer expectedProductNumber, String pageName) {

        yourCartFlow.getNumberOfProdcut(expectedProductNumber);
    }


}
