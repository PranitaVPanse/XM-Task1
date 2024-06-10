package TestRunner;

import org.testng.annotations.Test;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "/XM_Task1/src/test/resources/feature/XM_Task1.feature", // Location of your feature files
    glue = {"stepDefinitions"}, // Package where your step definitions are located
    plugin = {"pretty", "html:target/cucumber-reports"}, // Plugins for reporting
    tags = "@Test", // Tags to include/exclude specific scenarios
    monochrome = true // Makes the console output more readable
)

@Test
public class TestRunner extends AbstractTestNGCucumberTests {
	
	
}


