Feature: Knowledge Base getData endpoint Testing

  Scenario: Test Production KB is Up
    Given I'm using the production API environment
    When Hit Url "/heartbeat"
    Then Response returns status is Up

  Scenario: Test basic getData call works
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataBasicInput payload
    Then Response returns valid json in response to getDataBasicOutput

  Scenario Outline: Test <datasetName> getData call works
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataDatasetsInput payload of dataset <datasetName>
    Then Number of records returned is greater than zero
    And None of the properties in the response is null
    And Response contains all the phenotypes of <datasetName>
    And BETA and P_Value is returned for all phenotypes
    And Response returns with valid output data of <datasetName> contained in getDataDatasetsOutput
    Examples:
      | datasetName               |
      | AMPLOAD_7_broad_mdv1      |
      | AMPLOAD_7_metabo_mdv1     |
      | AMPLOAD_10_mdv1           |
      | AMPLOAD_7_affymetrix_mdv1 |
      | GWAS_EXTEND_mdv1          |
      | AMPLOAD_36_mdv1           |
      | AMPLOAD_7_illumina_mdv1   |
      | GWAS_OxBB_mdv25           |
      | AMPLOAD_7_exome_mdv1      |

  Scenario: Test getData for UNKNOWN_DATASET returns empty properties
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataBasicInput payload of dataset UNKNOWN_DATASET
    Then Number of records returned is greater than zero
    And Response returns output of UNKNOWN_DATASET without BETA and P_Value

  Scenario: Test getData for AMPLOAD_36_mdv1 with unknown phenotype(ADIPONECTIN)
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataDatasetsInput with AMPLOAD_36_mdv1 and unknown phenotype(ADIPONECTIN)
    Then Number of records returned is greater than zero
    And Response returns output of AMPLOAD_36_mdv1 without BETA and P_Value



