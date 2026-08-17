package com.sauceUI.step_def.LoginPage;

import com.sauceUI.step_def.Checkout_Complete.CheckoutCompletePage;
import com.sauceUI.step_def.Checkout_Overview.CheckoutOverviewPage;
import com.sauceUI.step_def.Checkout_YourInformation.CheckoutYourInformationPage;
import com.sauceUI.step_def.ProductsPage.ProductsPage;
import com.sauceUI.step_def.YourCartPage.YourCartPage;
import org.junit.Assert;

import java.util.Map;

public class LoginFlow {

    LoginPage loginPage = new LoginPage();
    ProductsPage productsPage = new ProductsPage();

    YourCartPage yourCartPage = new YourCartPage();

    CheckoutYourInformationPage checkoutYourInformationPage = new CheckoutYourInformationPage();
    CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage();
    CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage();


    public void setInputBoxesValue(String credantials, Map<String, String> loginCredantialsValues, String inputBoxName) {
        if (credantials.equalsIgnoreCase("Username")) {
            loginPage.usernameInputBox.click();
            loginPage.usernameInputBox.sendKeys(loginCredantialsValues.get(credantials));
        } else if (credantials.equalsIgnoreCase("Password")) {
            loginPage.passwordInputBox.click();
            loginPage.passwordInputBox.sendKeys(loginCredantialsValues.get(credantials));
        }
    }

    public void clickBtn(String buttonName) {
        if (buttonName.equalsIgnoreCase("Login")) {
            loginPage.loginButton.click();
        } else if (buttonName.equalsIgnoreCase("Checkout")) {
            yourCartPage.getCheckoutBtn.click();
        } else if (buttonName.equalsIgnoreCase("Continue")) {
            checkoutYourInformationPage.getContinueBtn.click();
        } else if (buttonName.equalsIgnoreCase("Finish")) {
            checkoutOverviewPage.getFinishBtn.click();
        } else if (buttonName.equalsIgnoreCase("Back Home")) {
            checkoutCompletePage.getBackHomeBtn.click();
        }


    }


    public void userAccessExpectation(String expectedResult, Map<String, String> loginAccess, String username) {

        String accessUserName = loginAccess.get(username);
        String accessExpectedResult = loginAccess.get(expectedResult);


        if (accessUserName.equalsIgnoreCase("locked_out_user")) {
            String loginErrorMsgText = loginPage.loginErrorMsg.getText();
            Assert.assertTrue(accessExpectedResult.equalsIgnoreCase(loginErrorMsgText));

        } else if (accessUserName.equalsIgnoreCase("standard_user")
                || accessUserName.equalsIgnoreCase("problem_user")
                || accessUserName.equalsIgnoreCase("performance_glitch_user")
                || accessUserName.equalsIgnoreCase("error_user")
                || accessUserName.equalsIgnoreCase("visual_user")) {

            String pageNameText = productsPage.getpageName.getText();
            Assert.assertTrue(accessExpectedResult.equalsIgnoreCase(pageNameText));

        } else if (accessUserName.isEmpty()) {
            String loginErrorMsgText = loginPage.loginErrorMsg.getText();
            Assert.assertTrue(accessExpectedResult.equalsIgnoreCase(loginErrorMsgText));
        } else {
            Assert.fail("Undefined unknown user type: " + accessUserName);

        }
    }
}
