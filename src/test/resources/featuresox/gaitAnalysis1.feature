Feature: Knowledge Base Gait Analysis-1 endpoint Testing OXFORD

  Scenario: Test Production KB is Up
    Given A configured API environment
    When Hit URL "/heartbeat"
    Then Response return HTTP status OK
    And Response returns status is Up
