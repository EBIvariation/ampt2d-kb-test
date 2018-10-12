Feature: Knowledge Base getSampleData endpoint Testing

  Scenario: Test Production KB is Up
    Given A configured API environment
    When Hit URL "/heartbeat"
    Then Response returns status is Up

  Scenario Outline: Test <datasetName> getSampleData call works
    Given A configured API environment
    When Hit URL "/getSampleData" with getSampleDataDatasetsInput payload of sample dataset <datasetName>
    Then Number of records returned is greater than zero
    And Response returns with valid output data of <datasetName> contained in getSampleDataDatasetsOutput
    Examples:
      | datasetName                           |
      | SAMPLES_GWAS_EXTEND_mdv1              |
      | SAMPLES_GWAS_OxBB_mdv2                |
      | SAMPLES_AMPLOAD_7_illumina_GRS_mdv1   |
      | SAMPLES_GWAS_OxBB_GRS_mdv2            |
      | SAMPLES_GWAS_EXTEND_GRS_mdv1          |
      | SAMPLES_AMPLOAD_36_mdv1               |
      | SAMPLES_AMPLOAD_36_GRS_mdv1           |
      | SAMPLES_AMPLOAD_7_exome_mdv1          |
      | SAMPLES_AMPLOAD_7_metabo_mdv1         |
      | SAMPLES_AMPLOAD_7_affymetrix_mdv1     |
      | SAMPLES_AMPLOAD_7_broad_mdv1          |
      | SAMPLES_AMPLOAD_7_illumina_mdv1       |
      | SAMPLES_AMPLOAD_7_affymetrix_GRS_mdv1 |
      | SAMPLES_AMPLOAD_7_broad_GRS_mdv1      |

  Scenario: Test getSampleData with UNKNOWN_DATASET returns error
    Given A configured API environment
    When Hit URL "/getSampleData" with getSampleDataDatasetsInput payload of sample dataset UNKNOWN_DATASET
    Then Response returns error that did not find property in metadata for filter

  Scenario: Test getSampleData with SAMPLES_GWAS_EXTEND_mdv1 with t2d phenotype
    Given A configured API environment
    When Hit URL "/getSampleData" with getSampleDataDatasetsInput payload of sample dataset SAMPLES_GWAS_EXTEND_mdv1 with t2d phenotype
    Then Number of records returned is greater than zero
    And Response returns with valid output data of SAMPLES_GWAS_EXTEND_mdv1_T2D contained in getSampleDataDatasetsOutput