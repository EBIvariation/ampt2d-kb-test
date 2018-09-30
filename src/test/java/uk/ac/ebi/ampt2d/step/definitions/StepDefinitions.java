/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.ampt2d.step.definitions;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.ampt2d.KnowledgeBaseApi;
import uk.ac.ebi.ampt2d.configuration.KnowledgeBaseApiConfiguration;
import uk.ac.ebi.ampt2d.payload.JsonPayload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KnowledgeBaseApiConfiguration.class})
public class StepDefinitions {

    private static JsonPayload jsonPayload;

    @Autowired
    private KnowledgeBaseApi knowledgeBaseApi;
    private RequestSpecification request;
    private ValidatableResponse response;

    @Before
    public void before(Scenario scenario) throws Exception {
        request = RestAssured.with();
        if (jsonPayload == null) {
            jsonPayload = new JsonPayload();
        }
    }

    @Given("^I'm using the production API environment$")
    public void givenEnvironment() {
        request.given()
                .contentType(ContentType.JSON)
                .baseUri(knowledgeBaseApi.getBaseUri());
    }

    @When("^Hit Url \"([^\"]*)\"$")
    public void whenHitGetRequest(String path) {
        response = request.get(path).then();
    }

    @Then("^Response returns status is Up$")
    public void responseReturnsStatusIsUp() {
        response.contentType(ContentType.TEXT).body(containsString("\"message\":\"service is up, World\""));
    }

    @Then("^Response returns api version$")
    public void responseReturnsApiVersion() throws Throwable {
        response.body("is_error", equalTo(false));
        response.body("api_version", equalTo(knowledgeBaseApi.getApiVersion()));
    }

    @Then("^Response returns .* without error$")
    public void responseReturnsOutputWithoutError() throws Throwable {
        response.body("is_error", equalTo(false));
    }

    @When("^Hit Url \"([^\"]*)\" with ([^ ]*) payload$")
    public void hitUrlWithRequestWithBasicInputData(String path, String jsonFile) throws Throwable {
        String inputPayload = jsonPayload.getPayload(jsonFile);
        response = request.contentType(ContentType.JSON).body(inputPayload.toString()).post(path).then();
    }

    @Then("^Response returns valid json in response same as expected in (.*?)$")
    public void responseReturnsValidJson(String jsonFile) throws Throwable {
        String outputPayload = jsonPayload.getPayload(jsonFile);
        response.contentType(ContentType.JSON).body(equalTo(outputPayload));
    }

    @When("^Hit Url \"([^\"]*)\" with ([^ ]*) payload of dataset ([^ ]*)$")
    public void hitUrlWithRequestForIndividualDatasetForGetData(String path, String jsonFile, String datasetName)
            throws Throwable {
        String payload = jsonPayload.getPayload(jsonFile);
        Map<String, List<String>> datasetsToPhenotypes = getDatasetToPhenotypes();
        String datasetPayload = getInputJsonForGetData(datasetName, payload, datasetsToPhenotypes);
        response = request.contentType(ContentType.JSON).body(datasetPayload.toString()).post(path).then();
    }

    @Then("^Number of records returned is greater than zero$")
    public void numberOfRecordsReturnedIsGreaterThanZero() throws Throwable {
        response.body("numRecords", greaterThan(0));
    }

    @And("^Response contains all the phenotypes of (.*)$")
    public void responseContainsAllThePhenotypesOfTheDataset(String datasetName) throws Throwable {
        List<String> expectedPhenotypes = getDatasetToPhenotypes().get(datasetName);
        expectedPhenotypes.stream().forEach(expectedPhenotype -> response.body(containsString(expectedPhenotype)));
    }

    @And("^None of the properties in the response is null$")
    public void noneOfThePropertiesInTheResponseIsNullForTheDataset() throws Throwable {
        response.body(not(containsString("null")));
    }

    @And("^BETA and P_Value is returned for all phenotypes$")
    public void betaAndP_ValueIsReturnedForAllPhenotypesOfTheDataset() throws Throwable {
        response.body(containsString("P_VALUE")).body(containsString("BETA"));
    }

    @And("^Response returns with valid output data of ([^ ]*) contained in ([^ ]*)$")
    public void responseReturnsWithValidOutputDataOfEachDataset(String datasetName, String jsonFile) throws Exception {
        String outputPayload = jsonPayload.getPayload(jsonFile);
        JSONAssert.assertEquals(JsonPath.read(outputPayload, "$." + datasetName).toString(), response.extract().body().asString(),
                true);
    }

    @When("^Hit Url \"([^\"]*)\" with ([^ ]*) with ([^ ]*) and unknown phenotype\\(([^)]*)\\)$")
    public void hitGetDataUrlWithUnknownPhenotype(String path, String jsonFile, String datasetName, String phenotype)
            throws Throwable {
        String inputPayload = jsonPayload.getPayload(jsonFile);
        inputPayload = inputPayload.replaceAll("INPUT_DATASET", datasetName);
        String unknownPhenotypes = "[\"" + phenotype + "\"]";
        inputPayload = inputPayload.replaceAll("\"PHENOTYPES_OF_DATASET\"", unknownPhenotypes);
        response = request.contentType(ContentType.JSON).body(inputPayload.toString()).post(path).then();
    }

    @And("^Response returns output of ([^ ]*) without BETA and P_Value$")
    public void responseReturnsWithoutBETAAndP_Value(String datasetName) throws Throwable {
        response.body(not(containsString("BETA"))).body(not(containsString("P_VALUE")));
    }

    @When("^Hit Url \"([^\"]*)\" with ([^ ]*) payload of sample dataset (\\w*)$")
    public void hitUrlWithRequestForIndividualDatasetForGetSampleData(String path, String jsonFile, String datasetName)
            throws Throwable {
        String inputPayloadTemplate = jsonPayload.getPayload(jsonFile);
        String datasetPayload = inputPayloadTemplate.replaceAll("INPUT_DATASET", datasetName);
        response = request.contentType(ContentType.JSON).body(datasetPayload.toString()).post(path).then();
    }

    @Then("^Response returns error that did not find property in metadata for filter$")
    public void responseReturnsErrorThatDidNotFindPropertyForFilter() throws Throwable {
        response.body("is_error", equalTo(true));
        response.body("error_message", startsWith("Did not find property in metadata for filter"));
    }

    @When("^Hit Url \"([^\"]*)\" with ([^ ]*) payload of sample dataset (\\w*) with (\\w*) phenotype$")
    public void hitGetSampleDataWithDatasetAndPhenotype(String path, String jsonFile,
          String datasetName, String phenotype) throws Throwable {
        String datasetPayload = jsonPayload.getPayload(jsonFile);
        datasetPayload = datasetPayload.replaceAll("INPUT_DATASET", datasetName);
        datasetPayload = datasetPayload.replaceAll("BMI", phenotype);
        datasetPayload = datasetPayload.replaceAll("HDL_ADJ", phenotype);
        response = request.contentType(ContentType.JSON).body(datasetPayload.toString()).post(path).then();
    }

    private String getInputJsonForGetData(String datasetName, String payload, Map<String, List<String>> datasetsToPhenotypes) {
        String inputDatasetPayloadForGetData = payload;
        inputDatasetPayloadForGetData = inputDatasetPayloadForGetData.replaceAll("INPUT_DATASET", datasetName);
        List<String> listOfPhenotypes = datasetsToPhenotypes.get(datasetName);
        String listOfPhenotypesString = (listOfPhenotypes != null) ? listOfPhenotypes.toString() : new ArrayList<String>().toString();
        inputDatasetPayloadForGetData = inputDatasetPayloadForGetData.replaceAll("\"PHENOTYPES_OF_DATASET\"", listOfPhenotypesString);
        return inputDatasetPayloadForGetData;
    }

    private Map<String, List<String>> getDatasetToPhenotypes() throws Exception {
        String metadataPayload = jsonPayload.getPayload("getMetadata");
        Map<String, List<String>> datasetToPhenotypes = new HashMap<>();
        List<Object> sampleGroups = JsonPath.read(metadataPayload, "$.experiments.[*].sample_groups[*]");
        for (Object sampleGroup : sampleGroups) {
            String dataset = JsonPath.read(sampleGroup, "$.id");
            datasetToPhenotypes.put(dataset, JsonPath.read(sampleGroup, "$.phenotypes[*].name"));
        }
        return datasetToPhenotypes;
    }

}
