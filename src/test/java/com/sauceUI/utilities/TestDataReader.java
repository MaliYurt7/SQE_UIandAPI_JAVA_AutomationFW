package com.sauceUI.utilities;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestDataReader {


    public Map<String, String> readTestData(String fileName, String testcaseID) throws IOException, CsvException {
        List<String[]> fileDataSheet;
        String[] testData = null;
        Map<String, String> testDataMap =new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            fileDataSheet = reader.readAll();
            // r.forEach(x -> System.out.println(Arrays.toString(x)));
            for (int row = 0; row<fileDataSheet.size(); row++){
                if(fileDataSheet.get(row)[0].trim().equalsIgnoreCase(testcaseID)){
                    testDataMap.put("TestcaseID",testcaseID);
                    for(int column=0;column<fileDataSheet.get(row).length;column++){
                        testDataMap.put(fileDataSheet.get(0)[column].toString(),fileDataSheet.get(row)[column]);
                    }
                    break;
                }
            }
            System.out.println("**************"+testDataMap);
            return testDataMap;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }




}