package com.sauceUI.utilities;

//import azaap.hcs.modules.Login.LoginFlow;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks {


    @Before
    public void setUp(Scenario scenario) throws IOException {
        //   System.out.println("***************SCENARIO NAME"+scenario.getUri());
        // Driver.get().getWindowHandles();
        Driver.get().manage().timeouts().getPageLoadTimeout();
        Driver.get().manage().window().maximize();
        Driver.get().manage().deleteAllCookies();
        Driver.setTestEnvironment();  //This method will read the environment specific Data from configuration.json and load it into a statig hashmap testEnvironmentDetails.
        System.out.println("Driver.testEnvironmentDetails.get(\"url\") = " + Driver.testEnvironmentDetails.get("url"));
        Driver.get().get(Driver.testEnvironmentDetails.get("url"));  //Open the browser URL
       // utils.createDirectoryToDownloadFile(Driver.downloadFilepath);

    }

    @After
    public void tearDown(Scenario scenario) throws InterruptedException {
        if (scenario.isFailed()) {

            try {
//                if (ConfigurationReader.get("browserStack").equalsIgnoreCase("true")) {
//                    js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\"}}");
//                }
                // Capture screenshot
                final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot on Failure");
                // Create folder structure based on the current date
                String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String folderPath = "screenshots/" + dateFolder;
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs(); // Create directories if they don't exist
                }

                // Generate a unique file name using the scenario name and timestamp
                String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
                String screenshotName = folderPath + "/" + scenario.getName().replaceAll("[^a-zA-Z0-9]", "_")
                        + "_failed_" + timestamp + ".png";

                // Save the screenshot to the specified location
                FileUtils.writeByteArrayToFile(new File(screenshotName), screenshot);
                System.out.println("Screenshot saved: " + screenshotName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Driver.softAssert.assertAll();
        Driver.closeDriver();
    }



}
