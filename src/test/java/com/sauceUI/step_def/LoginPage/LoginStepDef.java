package com.sauceUI.step_def.LoginPage;

import com.opencsv.exceptions.CsvException;
import com.sauceUI.utilities.Driver;
import com.sauceUI.utilities.GenericUtils;
import io.cucumber.java.en.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class LoginStepDef {

    GenericUtils utils = new GenericUtils();
    LoginFlow loginFlow = new LoginFlow();

    @Given("retrieve {string} from LoginCredentials testdata file")
    public void retrieve_from_login_credentials_testdata_file(String testCaseID) throws IOException, CsvException, InvalidFormatException {
        utils.readTestDataFromCSVFile(testCaseID, "LoginCredantials");

    }

    @Given("user types {string} from {string} in {string} input box")
    public void user_types_from_in_input_box(String credantials, String testCaseID, String inputBoxName) {
        loginFlow.setInputBoxesValue(credantials, Driver.loginCredantialsTestData, inputBoxName);
    }

    @Given("user clicks the {string} button")
    public void user_clicks_the_button(String buttonName) {

        loginFlow.clickBtn(buttonName);
    }


    @Given("user get {string} from {string} based on the {string}")
    public void user_get_from_based_on_the(String expectedResult, String testCaseID, String username) {

        loginFlow.userAccessExpectation(expectedResult, Driver.loginCredantialsTestData, username);

    }


}
