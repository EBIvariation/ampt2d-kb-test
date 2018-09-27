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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KnowledgeBaseApiConfiguration.class})
public class StepDefinitions {

    private static final String PAYLOAD_PATH = "payloads";
    private static final String JSON_FILE_EXTENSION = ".json";
    private static final String METADATA = "metadata";

    @Autowired
    private KnowledgeBaseApi knowledgeBaseApi;
    private RequestSpecification request;
    private ValidatableResponse response;
    private Map<String, ValidatableResponse> datasetsResponse = new HashMap<>();

    @Before
    public void before(Scenario scenario) {
        request = RestAssured.with();
    }

    @Given("^I'm using the production API environment$")
    public void givenEnvironment() {
        request.given()
                .contentType(ContentType.JSON)
                .baseUri(knowledgeBaseApi.getBaseUri());
    }

    @When("^Hit Url \"([^\"]*)\"$")
    public void whenHitRequest(String path) {
        response = request.get(path).then();
    }

    @When("^Hit Url \"([^\"]*)\" with ([^ ]*) payload$")
    public void hitUrlWithRequestForBasicProperties(String path, String jsonFile) throws Throwable {
        String payload = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + jsonFile + JSON_FILE_EXTENSION).toURI())));
        response = request.contentType(ContentType.JSON).body(payload.toString()).post(path).then();
    }

    @When("^Hit Url \"([^\"]*)\" with ([^ ]*) payload of individual datasets$")
    public void hitUrlWithRequestForIndividualDataset(String path, String jsonFile) throws Throwable {
        String payload = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + jsonFile + JSON_FILE_EXTENSION).toURI())));
        Map<String, List<String>> datasetsToPhenotypes = getDatasetToPhenotypes();
        for (Map.Entry datasetToPhenotypes : datasetsToPhenotypes.entrySet()) {
            String datasetPayload = payload;
            datasetPayload = datasetPayload.replaceAll("INPUT_DATASET", datasetToPhenotypes.getKey().toString());
            datasetPayload = datasetPayload.replaceAll("\"PHENOTYPES_OF_DATASET\"", datasetToPhenotypes.getValue().toString());
            response = request.contentType(ContentType.JSON).body(datasetPayload.toString()).post(path).then();
            datasetsResponse.put(datasetToPhenotypes.getKey().toString(), response);
        }
    }

    @Then("^Response returns status is Up$")
    public void returnStatusCode() {
        response.contentType(ContentType.TEXT).body(containsString("\"message\":\"service is up, World\""));
    }

    @Then("^Response returns api version$")
    public void responseReturnsApiVersion() throws Throwable {
        response.body("is_error", equalTo(false));
        response.body("api_version", equalTo(knowledgeBaseApi.getApiVersion()));
    }

    @Then("^Response returns .* without error$")
    public void responseReturnsMetadata() throws Throwable {
        response.body("is_error", equalTo(false));
    }

    @Then("^Response returns valid (.*?) json$")
    public void responseReturnsValidJson(String jsonFile) throws Throwable {
        String payload = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + jsonFile + JSON_FILE_EXTENSION).toURI())));
        response.contentType(ContentType.JSON).body(equalTo(payload));
    }

    @Then("^Response returns with valid output data of each datasets matching ([^ ]*) respectively$")
    public void responseReturnsWithValidOutputDataOfEachDataset(String jsonFile) throws Exception {
        String payload = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + jsonFile + JSON_FILE_EXTENSION).toURI())));
        for (Map.Entry<String, ValidatableResponse> datasetResponse : datasetsResponse.entrySet()) {
            JSONAssert.assertEquals(JsonPath.read(payload, "$." + datasetResponse.getKey())
                    .toString(), datasetResponse.getValue().extract().body().jsonPath().get().toString(), true);
        }
    }

    private Map<String, List<String>> getDatasetToPhenotypes() throws Exception {
        String payload = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + METADATA + JSON_FILE_EXTENSION).toURI())));
        Map<String, List<String>> datasetToPhenotypes = new HashMap<>();
        List<Object> sampleGroups = JsonPath.read(payload, "$.experiments.[*].sample_groups[*]");
        for (Object sampleGroup : sampleGroups) {
            String dataset = JsonPath.read(sampleGroup, "$.id");
            datasetToPhenotypes.put(dataset, JsonPath.read(sampleGroup, "$.phenotypes[*].name"));
        }
        return datasetToPhenotypes;
    }
}