package com.sauceUI.utilities;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class ReportGenerator {

    public static void generateReport(){

        File reportOutPutDirectory = new File("target/cucumber-html-reports");
        List<String> jsonFiles = Collections.singletonList("target/cucumber-reports/cucumber.json");
        String osName = System.getProperty("os.name").toLowerCase();
        String buildNumber="1";
        String projectNAme = "zippopotam Report";
        Configuration configuration = new Configuration( reportOutPutDirectory, projectNAme);
        configuration.setBuildNumber(buildNumber);
        configuration.addClassifications("platform",osName);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles,configuration);
        reportBuilder.generateReports();

    }
}
