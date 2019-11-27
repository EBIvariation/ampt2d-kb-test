Feature: Knowledge Base getSampleData endpoint Testing OXFORD

  Scenario: Test Production KB is Up
    Given A configured API environment
    When Hit URL "/heartbeat"
    Then Response return HTTP status OK
    And Response returns status is Up

  Scenario Outline: Test <datasetName> getSampleData call works
    Given A configured API environment
    When Hit URL "/getSampleData" with getSampleDataDatasetsInput payload of sample dataset <datasetName>
    Then Response return HTTP status OK
    And Number of records returned is greater than zero
    And Response returns with valid output data of <datasetName> contained in getSampleDataDatasetsOutputOx
    Examples:
      | datasetName                |
      | SAMPLES_OxBB_mdv25         |

  Scenario: Test getSampleData with UNKNOWN_DATASET returns error
    Given A configured API environment
    When Hit URL "/getSampleData" with getSampleDataDatasetsInput payload of sample dataset UNKNOWN_DATASET
    Then Response returns error that did not find property in metadata for filter
