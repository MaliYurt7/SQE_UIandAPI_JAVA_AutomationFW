package com.sauceUI.utilities;

import com.opencsv.exceptions.CsvException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;


public class GenericUtils {
    //ExcelReader reader = new ExcelReader();
    TestDataReader csvFileReader = new TestDataReader();


    public void readTestDataFromCSVFile(String testcaseID, String testDataModuleName) throws IOException, InvalidFormatException, CsvException {

        switch (testDataModuleName) {
            case "LoginCredantials":
                Driver.loginCredantialsTestData.clear();
                Driver.loginCredantialsTestData = csvFileReader.readTestData(fetchFilePathFromPropertyFile(testDataModuleName), testcaseID.trim());
                System.out.println("*******************TEST DATA from CSV file is****" + Driver.loginCredantialsTestData);
                break;
            default:
                System.out.println("No Such module exists");
        }
    }

    public String fetchFilePathFromPropertyFile(String moduleName) {
        String filePath = "";
        switch (moduleName) {
            case "LoginCredantials":
                filePath = ConfigurationReader.get("loginCredentials_CSVFilePath");
                break;
            default:
                System.out.println("File path for given module doesn't exist");
        }
        return filePath;
    }








}
