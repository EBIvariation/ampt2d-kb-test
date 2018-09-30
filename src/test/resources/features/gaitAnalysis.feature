Feature: Knowledge Base Gait Analysis endpoint Testing

  Scenario: Test Production KB is Up
    Given I'm using the production API environment
    When Hit Url "/heartbeat"
    Then Response returns status is Up

  Scenario Outline: Test <datasetName> Gait Analysis works
    Given I'm using the production API environment
    When Hit Url "/burden/v1" with getGaitAnalysisInput payload of sample dataset <datasetName>
    Then Response returns output without error
    And Response returns with valid output data of <datasetName> contained in getGaitAnalysisOutput
    Examples:
      | datasetName                       |
      | SAMPLES_GWAS_EXTEND_mdv1          |
      | SAMPLES_GWAS_OxBB_mdv2            |
      | SAMPLES_AMPLOAD_36_mdv1           |
      | SAMPLES_AMPLOAD_7_exome_mdv1      |
      | SAMPLES_AMPLOAD_7_metabo_mdv1     |
      | SAMPLES_AMPLOAD_7_affymetrix_mdv1 |
      | SAMPLES_AMPLOAD_7_broad_mdv1      |
      | SAMPLES_AMPLOAD_7_illumina_mdv1   |

  Scenario: Test SAMPLES_GWAS_EXTEND_mdv1 with t2d phenotype Gait Analysis works
    Given I'm using the production API environment
    When Hit Url "/burden/v1" with getGaitAnalysisInput payload of sample dataset SAMPLES_GWAS_EXTEND_mdv1 with t2d phenotype
    Then Response returns output without error
    And Response returns with valid output data of SAMPLES_GWAS_EXTEND_mdv1_T2D contained in getGaitAnalysisOutput