Feature: Login functionality
    As a user I want to be able to login to the application

@smoke @regression
Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user logs in with "standard_user" and "secret_sauce"
    Then the inventory page should be displayed

@regression
Scenario: Login with missing username
    Given the user is on the login page
    When the user logs in with "" and "secret_sauce"
    Then an error message "Epic sadface: Username is required" should be displayed

@regression
Scenario: Login with missing password
    Given the user is on the login page
    When the user logs in with "standard_user" and ""
    Then an error message "Epic sadface: Password is required" should be displayed

@regression
Scenario Outline: Login with multiple invalid credentials
    Given the user is on the login page
    When the user logs in with "<username>" and "<password>"
    Then an error message "<error>" should be displayed

    #cmd + option + L in order to align the table
    Examples:
        | username      | password       | error                                                                     |
        | invalid_user  | wrong_password | Epic sadface: Username and password do not match any user in this service |
        |               | secret_sauce   | Epic sadface: Username is required                                        |
        | standard_user |                | Epic sadface: Password is required                                        |