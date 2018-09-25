Feature: Knowledge Base Testing

  Scenario: Assert Staging KB is Up
    Given I'm using the staging API environment
    When Hit Url "/heartbeat"
    Then Response returns status is Up

  Scenario: Assert Staging KB getMetadata returns metadata without error
    Given I'm using the staging API environment
    When Hit Url "/getMetadata"
    Then Response returns metadata

  Scenario: Assert Staging KB getMetadata returns SampleMetadata without error
    Given I'm using the staging API environment
    When Hit Url "/getSampleMetadata"
    Then Response returns SampleMetadata

  Scenario: Assert Staging KB Metadata Api Version is 2.0
    Given I'm using the staging API environment
    When Hit Url "/status"
    Then Response returns api version

  Scenario: Assert Staging KB getMetadata returns valid json
    Given I'm using the staging API environment
    When Hit Url "/getMetadata"
    Then Response returns valid metadata json

  Scenario: Assert Staging KB getSampleMetadata returns valid json
    Given I'm using the staging API environment
    When Hit Url "/getSampleMetadata"
    Then Response returns valid sampleMetadata json




