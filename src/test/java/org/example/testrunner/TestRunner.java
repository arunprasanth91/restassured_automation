package org.example.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/org/example/features", glue = {"org.example.stepDefinitions"}, tags = "@AddPlace",
plugin = "json:target/jsonReports/cucumber-report.json")

// Using Testng for scenario level parallelism.
public class TestRunner extends AbstractTestNGCucumberTests {


	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios(){
		return super.scenarios();
	}
}
