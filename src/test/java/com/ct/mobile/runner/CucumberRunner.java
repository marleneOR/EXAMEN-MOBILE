package com.ct.mobile.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.ct.mobile.glue", "com.ct.mobile.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        tags = "@LoginTest"
)
public class CucumberRunner {

}
