package uk.ac.ebi.ampt2d;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty","html:target/cucumber","json:target/cucumber.json"},
        features = {"classpath:features"})
public class KnowledgeBaseServicesTest {

}