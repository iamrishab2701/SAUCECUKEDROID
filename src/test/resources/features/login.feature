Feature: Login Functionality

  Scenario: Successful login
    Given I launch the app
    When I enter valid credentials
    And I click on login button
    Then I should see the home screen