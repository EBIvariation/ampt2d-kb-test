package uk.ac.ebi.ampt2d;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinitions {

    private static final String BASE_STAGING_URI = "https://www.ebi.ac.uk/ega/ampt2d/dev/dig-genome-services";
    private static final Integer NUM_OF_EXPERIMENTS_IN_METADATA = 9;
    private static final Integer NUM_OF_EXPERIMENTS_IN_SAMPLE_METADATA = 8;
    private static final String API_VERSION = "2.0";
    private RequestSpecification request;
    private ValidatableResponse response;

    @Before
    public void before(Scenario scenario) {
        request = RestAssured.with();

    }

    @Given("^I'm using the staging API environment$")
    public void givenEnvironment() {
        request.given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_STAGING_URI);
    }

    @When("^Hit Url \"([^\"]*)\"$")
    public void whenHitRequest(String path) {
        response = request.get(path).then();
    }

    @Then("^Response returns status is Up$")
    public void returnStatusCode() {
        response.contentType(ContentType.TEXT).body(containsString("\"message\":\"service is up, World\""));
    }

    @Then("^Response returns metadata$")
    public void responseReturnsMetadata() throws Throwable {
        response.body("is_error", equalTo(false));
        response.body("numRecords", equalTo(NUM_OF_EXPERIMENTS_IN_METADATA));
    }

    @Then("^Response returns SampleMetadata$")
    public void responseReturnsSampleMetadata() throws Throwable {
        response.body("is_error", equalTo(false));
        response.body("numRecords", equalTo(NUM_OF_EXPERIMENTS_IN_SAMPLE_METADATA));
    }

    @Then("^Response returns api version$")
    public void responseReturnsApiVersion() throws Throwable {
        response.body("is_error", equalTo(false));
        response.body("api_version", equalTo(API_VERSION));
    }

    @Then("^Response returns valid ([^ ]*) json$")
    public void responseReturnsValidJson(String jsonFile) throws Throwable {
        String payload = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(jsonFile + ".json").toURI())));
        response.body(equalTo(payload));
    }
}
