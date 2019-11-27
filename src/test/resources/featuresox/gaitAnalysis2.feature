Feature: Knowledge Base Gait Analysis-2 endpoint Testing OXFORD
#  This test requires 'application.properties' with value "kb.node.baseUri=http://mccarthy.well.ox.ac.uk/dig-analysis-engine"

  Scenario Outline: Test <datasetName> Gait Analysis works
    Given A configured API environment
    When Hit URL "/burden/v1" with getGaitAnalysisInputOx payload of sample dataset <datasetName>
    Then Response return HTTP status OK
    And Response returns output without error
    And Response returns with valid output data of <datasetName> contained in getGaitAnalysisOutputOx
    Examples:
      | datasetName                   |
      | samples_OxBB_mdv25            |
