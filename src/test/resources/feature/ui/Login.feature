@LoginPage @Regression
Feature: UI verification

  @AccessMainPage
  Scenario Outline: verify that users can access the main page
    And retrieve "<TestCaseID>" from LoginCredentials testdata file
    And user types "username" from "<TestCaseID>" in "Username" input box
    And user types "password" from "<TestCaseID>" in "Password" input box
    And user clicks the "Login" button
    And user get "expectedResult" from "<TestCaseID>" based on the "username"

    Examples:
      | TestCaseID                |
      | SuccessAccess_Standard    |
      | InvalidAccess_LockedOut   |
      | SuccessAccess_Problem     |
      | SuccessAccess_Performance |
      | SuccessAccess_Error       |
      | SuccessAccess_Visual      |
      | EmptyValue                |


