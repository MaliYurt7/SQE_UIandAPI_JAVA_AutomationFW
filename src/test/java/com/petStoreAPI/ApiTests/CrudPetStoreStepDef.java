package com.petStoreAPI.ApiTests;

import com.petStoreAPI.DataFiles.jsonPayload;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.Map;

import static io.restassured.RestAssured.given;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class CrudPetStoreStepDef {
    public Response response;
    public static int petId;

    @Given("PetStore API base URL is {string}")
    public void pet_store_api_base_url_is(String string) {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @When("user sends POST request to {string} with valid pet payload")
    public void user_sends_post_request_to_with_valid_pet_payload(String pet) {
        pet = "/pet";
        response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(jsonPayload.addPets())
                .when()
                .post(pet)
                .then()
                .log().all()
                .extract()
                .response();

        petId = response.jsonPath().getInt("id");

    }

    @Then("the following response expected")
    public void the_following_response_expected(DataTable dataTable) {
        Map<String, String> responses = dataTable.asMap(String.class, String.class);
        response.then().assertThat()
                .statusCode(Integer.parseInt(responses.get("responseStatusCode")))
                .body("id", equalTo(Integer.parseInt(responses.get("id"))))
                .body("status", equalTo(responses.get("status")))
                .body("category.name", equalTo(responses.get("categoryName")))
                .header("Server", equalTo(responses.get("headerServer")));


    }

    @When("user sends PUT request to {string} with update pet payload")
    public void user_sends_put_request_to_with_update_pet_payload(String pet) {
        pet = "/pet";
        response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(jsonPayload.updateExistingPets())
                .when()
                .log().all()
                .put(pet)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Then("the following response expected for PUT method")
    public void the_following_response_expected_for_put_method(DataTable dataTable) {
        Map<String, String> getPutResponses = dataTable.asMap(String.class, String.class);
        response.then().assertThat()
                .statusCode(Integer.parseInt(getPutResponses.get("responseStatusCode")))
                .body("id", equalTo(Integer.parseInt(getPutResponses.get("id"))))
                .body("status", equalTo(getPutResponses.get("status")))
                .body("category.name", equalTo(getPutResponses.get("categoryName")))
                .header("Server", equalTo(getPutResponses.get("headerServer")))
                .header("access-control-allow-methods", equalTo(getPutResponses.get("AccessControlAllowMethods")));

    }


    @When("user sends GET request to {string} with created pet id")
    public void user_sends_get_request_to_with_created_pet_id(String pet) {
        pet = "/pet/" + petId;
        response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .get(pet)
                .then()
                .log().all()
                .extract()
                .response();

    }

    @Then("the following response expected for GET method")
    public void the_following_response_expected_for_get_method(DataTable dataTable) {
        Map<String, String> getMethodResponses = dataTable.asMap(String.class, String.class);
        response.then().assertThat()
                .statusCode(Integer.parseInt(getMethodResponses.get("responseStatusCode")))
                .body("id", equalTo(Integer.parseInt(getMethodResponses.get("id"))))
                .body("status", equalTo(getMethodResponses.get("status")))
                .body("category.name", equalTo(getMethodResponses.get("categoryName")))
                .body("name", equalTo(getMethodResponses.get("name")))
                .header("Server", equalTo(getMethodResponses.get("headerServer")))
                .header("access-control-allow-headers", equalTo(getMethodResponses.get("AccessControlAllowHeaders")));
    }


    @When("user sends DELETE request to {string} with created pet id")
    public void user_sends_delete_request_to_with_created_pet_id(String pet) {
        pet = "/pet/" + petId;
        response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .delete(pet)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Then("the following response expected for Delete method")
    public void the_following_response_expected_for_delete_method(DataTable dataTable) {
        Map<String, String> getDeleteMethodResponses = dataTable.asMap(String.class, String.class);
        response.then().assertThat()
                .statusCode(Integer.parseInt(getDeleteMethodResponses.get("responseStatusCode")))
                .body("code", equalTo(Integer.parseInt(getDeleteMethodResponses.get("code"))))
                .body("type", equalTo(getDeleteMethodResponses.get("type")))
                .body("message", equalTo(getDeleteMethodResponses.get("message")))
                .body("name", equalTo(getDeleteMethodResponses.get("name")))
                .header("content-type", equalTo(getDeleteMethodResponses.get("contentType")))
                .header("access-control-allow-headers", equalTo(getDeleteMethodResponses.get("AccessControlAllowHeaders")));
    }

    @When("user sends GET request to {string} with created pet id again")
    public void user_sends_get_request_to_with_created_pet_id_again(String pet) {
        pet = "/pet/" + petId;
        response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .get(pet)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Then("the following response expected for new Get method call")
    public void the_following_response_expected_for_new_get_method_call(DataTable dataTable) {
        Map<String, String> getMethodCallResponsesAgain = dataTable.asMap(String.class, String.class);
        response.then().assertThat()
                .statusCode(Integer.parseInt(getMethodCallResponsesAgain.get("responseStatusCode")))
                .body("code", equalTo(Integer.parseInt(getMethodCallResponsesAgain.get("code"))))
                .body("type", equalTo(getMethodCallResponsesAgain.get("type")))
                .body("message", equalTo(getMethodCallResponsesAgain.get("message")))
                .header("server", equalTo(getMethodCallResponsesAgain.get("server")))
                .header("content-type", equalTo(getMethodCallResponsesAgain.get("contentType")));
    }


    @Then("response status code should be {int}")
    public void response_status_code_should_be(Integer expectedResponseCode) {
        response.then().statusCode(expectedResponseCode);

    }

    @Then("response body should match JSON schema {string}")
    public void response_body_should_match_json_schema(String string) {
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("api_TestData/get-json-schema-Pet.json"));

    }

    @When("user sends POST request to {string} with invalid pet status payload")
    public void user_sends_post_request_to_with_invalid_pet_status_payload(String string) {
        response = given().log().all().header("Content-Type", "application/json").body(jsonPayload.addInvalidIdPetStatus())
                .when().log().all().post("/pet");
    }

    @When("user sends POST request to {string} with invalid string number pet id payload")
    public void user_sends_post_request_to_with_invalid_string_number_pet_id_payload(String string) {
        response = given().log().all().header("Content-Type", "application/json").body(jsonPayload.addInvalidIdPetId())
                .when().log().all().post("/pet");
    }

    @Then("response status code should not be {int}")
    public void response_status_code_should_not_be(Integer notEexpectedResponseCode) {
        response.then().statusCode(not(equalTo(notEexpectedResponseCode)));
    }
}
