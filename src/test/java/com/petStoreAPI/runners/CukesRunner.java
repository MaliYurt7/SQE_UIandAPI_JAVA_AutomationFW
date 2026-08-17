package com.petStoreAPI.runners;


import com.sauceUI.utilities.ReportGenerator;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/rerun.txt"},
        features = "src/test/resources",
        glue = "com/petStoreAPI",
        dryRun = false,
        tags = "@RegressionPCrud"
)

public class CukesRunner {

    @AfterClass
    public static void report() {
        ReportGenerator.generateReport();
    }
}
