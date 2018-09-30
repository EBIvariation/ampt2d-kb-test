Feature: Knowledge Base Basic Endpoints Testing

  Scenario: Assert Production KB is Up
    Given I'm using the production API environment
    When Hit Url "/heartbeat"
    Then Response returns status is Up

  Scenario: Assert Production KB Metadata Api Version is 2.0
    Given I'm using the production API environment
    When Hit Url "/status"
    Then Response returns api version

  Scenario: Assert Production KB getMetadata returns metadata without error
    Given I'm using the production API environment
    When Hit Url "/getMetadata"
    Then Response returns getMetadata without error

  Scenario: Assert Production KB getSampleMetadata returns SampleMetadata without error
    Given I'm using the production API environment
    When Hit Url "/getSampleMetadata"
    Then Response returns getSampleMetadata without error

  Scenario: Assert Production KB getMetadata returns valid json
    Given I'm using the production API environment
    When Hit Url "/getMetadata"
    Then Response returns valid json in response same as expected in getMetadata

  Scenario: Assert Production KB getSampleMetadata returns valid json
    Given I'm using the production API environment
    When Hit Url "/getSampleMetadata"
    Then Response returns valid json in response same as expected in getSampleMetadata