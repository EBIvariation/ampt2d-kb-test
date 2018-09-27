Feature: Knowledge Base getData endpoint Testing

  Scenario: Test Production KB is Up
    Given I'm using the production API environment
    When Hit Url "/heartbeat"
    Then Response returns status is Up

  Scenario: Test basic getData call works
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataBasicInput payload
    Then Response returns valid getDataBasicOutput json

  Scenario Outline: Test <datasetName> getData call works
    Given I'm using the production API environment
    When Hit Url "/getData" with getDataDatasetsInput payload of dataset <datasetName>
    Then Response returns with valid output data of <datasetName> contained in getDataDatasetsOutput
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



