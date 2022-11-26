Feature: End to End Tests for Employee API
  Description: The purpose of these tests are to cover End to End happy flows for customer

    Scenario: User is able to Add and Remove a book.
      Given A list of employees are available
      When I add a employee
      Then The employee is added
      When I remove a employee
      Then The employee is removed