@ProductPage @Regression
Feature:Product page testing


  @ProductPictures
  Scenario Outline: verify that users shouls see different pictureon the product page
    And retrieve "<TestCaseID>" from LoginCredentials testdata file
    And user types "username" from "<TestCaseID>" in "Username" input box
    And user types "password" from "<TestCaseID>" in "Password" input box
    And user clicks the "Login" button
    When page should navigate to "Products" page
    Then user should see the different product pictures

    Examples:
      | TestCaseID            |
      | SuccessAccess_Problem |


      @errorUser
  Scenario Outline:verify that users should be able to click all the products without any restriction
    And retrieve "<TestCaseID>" from LoginCredentials testdata file
    And user types "username" from "<TestCaseID>" in "Username" input box
    And user types "password" from "<TestCaseID>" in "Password" input box
    And user clicks the "Login" button
    Then page should navigate to "Products" page
    And user get "expectedResult" from "<TestCaseID>" based on the "username"
    When user adds the following product to cart
      | productName              |
      | Sauce Labs Bolt T-Shirt  |

    Examples:
      | TestCaseID          |
      | SuccessAccess_Error |
