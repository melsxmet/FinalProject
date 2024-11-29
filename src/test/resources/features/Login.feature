Feature: Login scenarios
  Scenario: admin login
    Given user is able to access to hrms app
    When user enter valid credentials
    And click on login button
    Then user navigate to dashboard page