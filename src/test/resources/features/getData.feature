Feature: Knowledge Base getData endpoint Testing

  Scenario: Assert Production KB is Up
    Given I'm using the production API environment
    When Hit Url "/heartbeat"
    Then Response returns status is Up

  Scenario: Assert basic getData call works
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataBasicInput payload
    Then Response returns valid getDataBasicOutput json

  Scenario: Assert Individual datasets getData call works
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataDatasetsInput payload of individual datasets
    Then Response returns with valid output data of each datasets matching getDataDatasetsOutput respectively
