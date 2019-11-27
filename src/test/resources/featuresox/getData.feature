Feature: Knowledge Base getData endpoint Testing OXFORD

  Scenario: Test Production KB is Up
    Given A configured API environment
    When Hit URL "/heartbeat"
    Then Response return HTTP status OK
    And Response returns status is Up

  Scenario: Test basic getData call works
    Given A configured API environment
    When Hit URL "/getData" with getDataBasicInput payload
    Then Response return HTTP status OK
    And Response returns data without error
    And Number of records returned is greater than zero
    And None of the properties in the response is null
    And Response is the expected JSON getDataBasicOutput

  Scenario Outline: Test <datasetName> getData call works
    Given A configured API environment
    When Hit URL "/getData" with getDataDatasetsInput payload of dataset <datasetName>
    Then Response return HTTP status OK
    And Number of records returned is greater than zero
    And None of the properties in the response is null
    And Response contains all the phenotypes of <datasetName>
    And BETA and P_Value is returned for all phenotypes
    And Response returns with valid output data of <datasetName> contained in getDataDatasetsOutputOx
    Examples:
      | datasetName          |
      | GWAS_OxBB_MDV25      |

  Scenario: Test getData for UNKNOWN_DATASET returns empty properties
    Given A configured API environment
    When Hit URL "/getData" with getDataBasicInput payload of dataset UNKNOWN_DATASET
    Then Number of records returned is greater than zero
    And Response returns output of UNKNOWN_DATASET without BETA and P_Value

  Scenario: Test getData for GWAS_OxBB_MDV25 with unknown phenotype for the dataset returns empty properties
    Given A configured API environment
    When Hit URL "/getData" with GWAS_OxBB_MDV25 dataset and ADIPONECTIN phenotype using getDataDatasetsInput payload
    Then Number of records returned is greater than zero
    And Response returns output of GWAS_OxBB_MDV25 without BETA and P_Value