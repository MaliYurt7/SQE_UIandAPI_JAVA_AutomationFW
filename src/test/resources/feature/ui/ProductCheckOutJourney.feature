@ProductCheckout @smokeTest @Regression
Feature:Product Check out journey


  @checkout2Products
  Scenario Outline: verify that users can access the main page
    And retrieve "<TestCaseID>" from LoginCredentials testdata file
    And user types "username" from "<TestCaseID>" in "Username" input box
    And user types "password" from "<TestCaseID>" in "Password" input box
    And user clicks the "Login" button
    Then page should navigate to "Products" page
    When user adds the following products to cart
      | productName              | actualPrice |
      | Sauce Labs Bolt T-Shirt  | $15.99      |
      | Sauce Labs Fleece Jacket | $49.99      |

    And user should see number 2 on  the shopping card link
    Then click the shopping cart link
    Then page should navigate to "Your Cart" page
    Then 2 products should be displayed on the "Your Cart" page
    And user clicks the "Checkout" button
    Then page should navigate to "Checkout: Your Information" page
    Then user enters the following details
      | FirstName      | Micheal |
      | LastName       | Tyson   |
      | Zip/PostalCode | ST1 4EX |
    And user clicks the "Continue" button
    Then page should navigate to "Checkout: Overview" page
    Then 2 products should be displayed on the "Checkout: Overview" page
    Then the total price should  Item total: $65.98
    And user clicks the "Finish" button
    Then page should navigate to "Checkout: Complete!" page
    Then user should see the "Thank you for your order!" message
    Then user clicks the "Back Home" button
    Then page should navigate to "Products" page
    Then user should see 6 "Add to cart" button

    Examples:
      | TestCaseID             |
      | SuccessAccess_Standard |
#      | InvalidAccess_LockedOut   |
#      | SuccessAccess_Problem     |
#      | SuccessAccess_Performance |
#      | SuccessAccess_Error       |
#      | SuccessAccess_Visual      |
