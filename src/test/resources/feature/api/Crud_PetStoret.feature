@RegressionPCrud
Feature: PetStore API - CRUD Operations for Pet

  Background:
    Given PetStore API base URL is "https://petstore.swagger.io/v2"

  Scenario: Positive test - create, update, get and delete a pet
    When user sends POST request to "/pet" with valid pet payload
    Then the following response expected
      | responseStatusCode | 200                    |
      | id                 | 123456                 |
      | status             | pending                |
      | categoryName       | Kangal                 |
      | headerServer       | Jetty(9.2.9.v20150224) |


    When user sends PUT request to "/pet" with update pet payload
    Then the following response expected for PUT method
      | responseStatusCode        | 200                    |
      | id                        | 123456                 |
      | status                    | available              |
      | categoryName              | Kangal                 |
      | headerServer              | Jetty(9.2.9.v20150224) |
      | AccessControlAllowMethods | GET, POST, DELETE, PUT |

    When user sends GET request to "/pet/{petId}" with created pet id

    Then the following response expected for GET method
      | responseStatusCode        | 200                                  |
      | id                        | 123456                               |
      | status                    | available                            |
      | categoryName              | Kangal                               |
      | name                      | KARABAS                              |
      | headerServer              | Jetty(9.2.9.v20150224)               |
      | AccessControlAllowHeaders | Content-Type, api_key, Authorization |


    When user sends DELETE request to "/pet/{petId}" with created pet id
    Then the following response expected for Delete method
      | responseStatusCode        | 200                                  |
      | code                      | 200                                  |
      | type                      | unknown                              |
      | message                   | 123456                               |
      | contentType               | application/json                     |
      | AccessControlAllowHeaders | Content-Type, api_key, Authorization |


    When user sends GET request to "/pet/{petId}" with created pet id again
    Then the following response expected for new Get method call
      | responseStatusCode | 404                    |
      | code               | 1                      |
      | type               | error                  |
      | message            | Pet not found          |
      | server             | Jetty(9.2.9.v20150224) |
      | contentType        | application/json       |


@SchemaTest
  Scenario: JSON schema validation for POST /pet response
    When user sends POST request to "/pet" with valid pet payload
    Then response status code should be 200
    And response body should match JSON schema "apiTestData/get-json-schema-Pet.json"

  @InvalidStatus
  Scenario: Negative test - invalid pet status value
    When user sends POST request to "/pet" with invalid pet status payload
    Then response status code should be 400


  @InvalidPetID
  Scenario: Negative test - invalid string number pet id
    When user sends POST request to "/pet" with invalid string number pet id payload
    Then response status code should not be 200
