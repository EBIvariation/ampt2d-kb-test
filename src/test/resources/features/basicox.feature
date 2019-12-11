Feature: Knowledge Base Basic Endpoints Testing OXFORD

  Scenario: Assert Production KB is Up
    Given A configured OX API environment
    When Hit URL "/heartbeat"
    Then Response return HTTP status OK
    And Response returns status is Up

  Scenario: Assert Production KB Metadata Api Version is 2.0
    Given A configured OX API environment
    When Hit URL "/status"
    Then Response return HTTP status OK
    And Response returns OX API version

  Scenario: Assert Production KB getMetadata returns metadata without error
    Given A configured OX API environment
    When Hit URL "/getMetadata"
    Then Response return HTTP status OK
    And Response returns getMetadata without error

  Scenario: Assert Production KB getSampleMetadata returns SampleMetadata without error
    Given A configured OX API environment
    When Hit URL "/getSampleMetadata"
    Then Response return HTTP status OK
    And Response returns getSampleMetadata without error