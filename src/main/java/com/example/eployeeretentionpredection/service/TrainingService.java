
package com.example.eployeeretentionpredection.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class TrainingService {

    public void trainModel() throws Exception {
        // Load the Excel file
        FileInputStream excelFile = new FileInputStream(System.getProperty("user.dir") + "/upload/data-source/employee_data.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        // Collect unique values for nominal attributes
        Set<String> uniqueAddresses = new HashSet<>();
        Set<String> uniqueJobTitles = new HashSet<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row
            uniqueAddresses.add(row.getCell(2).getStringCellValue());
            uniqueJobTitles.add(row.getCell(3).getStringCellValue());
        }

        // Define attributes
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("age"));
        attributes.add(new Attribute("gender", List.of("male", "female", "other")));
        attributes.add(new Attribute("address", new ArrayList<>(uniqueAddresses)));
        attributes.add(new Attribute("jobTitle", new ArrayList<>(uniqueJobTitles)));
        attributes.add(new Attribute("department", List.of("IT", "Operations", "HR", "Sales", "Marketing")));
        attributes.add(new Attribute("lengthOfService"));
        attributes.add(new Attribute("promotionsReceived"));
        attributes.add(new Attribute("trainingOpportunities", List.of("yes", "no")));
        attributes.add(new Attribute("workingEnvironment"));
        attributes.add(new Attribute("managementQuality"));
        attributes.add(new Attribute("jobSatisfaction"));
        attributes.add(new Attribute("personalDevelopmentOpportunities"));
        attributes.add(new Attribute("leftReason", List.of("personal", "job_related", "management", "other")));
        attributes.add(new Attribute("likelyToLeave", List.of("yes", "no")));

        // Create Instances object
        Instances dataSet = new Instances("employeeData", attributes, 0);
        dataSet.setClassIndex(dataSet.numAttributes() - 1);

        // Populate Instances object with data from Excel
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            DenseInstance instance = new DenseInstance(dataSet.numAttributes());
            instance.setValue(attributes.get(0), row.getCell(0).getNumericCellValue());
            instance.setValue(attributes.get(1), row.getCell(1).getStringCellValue());
            instance.setValue(attributes.get(2), row.getCell(2).getStringCellValue());
            instance.setValue(attributes.get(3), row.getCell(3).getStringCellValue());
            instance.setValue(attributes.get(4), row.getCell(4).getStringCellValue());
            instance.setValue(attributes.get(5), row.getCell(5).getNumericCellValue());
            instance.setValue(attributes.get(6), row.getCell(6).getNumericCellValue());
            instance.setValue(attributes.get(7), row.getCell(7).getStringCellValue());
            instance.setValue(attributes.get(8), row.getCell(8).getNumericCellValue());
            instance.setValue(attributes.get(9), row.getCell(9).getNumericCellValue());
            instance.setValue(attributes.get(10), row.getCell(10).getNumericCellValue());
            instance.setValue(attributes.get(11), row.getCell(11).getNumericCellValue());
            instance.setValue(attributes.get(12), row.getCell(12).getStringCellValue());
            instance.setValue(attributes.get(13), row.getCell(13).getStringCellValue());

            dataSet.add(instance);
        }

        workbook.close();

        // Train the Random Forest model
        RandomForest rf = new RandomForest();
        rf.buildClassifier(dataSet);

        // Save the model
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("upload/random_forest_model.model"))) {
            oos.writeObject(rf);
        }
    }

    public void trainModel(MultipartFile file) throws Exception {
        Files.write(Path.of(System.getProperty("user.dir") + "/upload/data-source/" + file.getOriginalFilename()), file.getBytes());

        // Load the Excel file
        FileInputStream excelFile = new FileInputStream(System.getProperty("user.dir") + "/upload/data-source/employee_data.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        // Collect unique values for nominal attributes
        Set<String> uniqueAddresses = new HashSet<>();
        Set<String> uniqueJobTitles = new HashSet<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row
            uniqueAddresses.add(row.getCell(2).getStringCellValue());
            uniqueJobTitles.add(row.getCell(3).getStringCellValue());
        }

        // Define attributes
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("age"));
        attributes.add(new Attribute("gender", List.of("male", "female", "other")));
        attributes.add(new Attribute("address", new ArrayList<>(uniqueAddresses)));
        attributes.add(new Attribute("jobTitle", new ArrayList<>(uniqueJobTitles)));
        attributes.add(new Attribute("department", List.of("IT", "Operations", "HR", "Sales", "Marketing")));
        attributes.add(new Attribute("lengthOfService"));
        attributes.add(new Attribute("promotionsReceived"));
        attributes.add(new Attribute("trainingOpportunities", List.of("yes", "no")));
        attributes.add(new Attribute("workingEnvironment"));
        attributes.add(new Attribute("managementQuality"));
        attributes.add(new Attribute("jobSatisfaction"));
        attributes.add(new Attribute("personalDevelopmentOpportunities"));
        attributes.add(new Attribute("leftReason", List.of("personal", "job_related", "management", "other")));
        attributes.add(new Attribute("likelyToLeave", List.of("yes", "no")));

        // Create Instances object
        Instances dataSet = new Instances("employeeData", attributes, 0);
        dataSet.setClassIndex(dataSet.numAttributes() - 1);

        // Populate Instances object with data from Excel
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            DenseInstance instance = new DenseInstance(dataSet.numAttributes());
            instance.setValue(attributes.get(0), row.getCell(0).getNumericCellValue());
            instance.setValue(attributes.get(1), row.getCell(1).getStringCellValue());
            instance.setValue(attributes.get(2), row.getCell(2).getStringCellValue());
            instance.setValue(attributes.get(3), row.getCell(3).getStringCellValue());
            instance.setValue(attributes.get(4), row.getCell(4).getStringCellValue());
            instance.setValue(attributes.get(5), row.getCell(5).getNumericCellValue());
            instance.setValue(attributes.get(6), row.getCell(6).getNumericCellValue());
            instance.setValue(attributes.get(7), row.getCell(7).getStringCellValue());
            instance.setValue(attributes.get(8), row.getCell(8).getNumericCellValue());
            instance.setValue(attributes.get(9), row.getCell(9).getNumericCellValue());
            instance.setValue(attributes.get(10), row.getCell(10).getNumericCellValue());
            instance.setValue(attributes.get(11), row.getCell(11).getNumericCellValue());
            instance.setValue(attributes.get(12), row.getCell(12).getStringCellValue());
            instance.setValue(attributes.get(13), row.getCell(13).getStringCellValue());

            dataSet.add(instance);
        }

        workbook.close();

        // Train the Random Forest model
        RandomForest rf = new RandomForest();
        rf.buildClassifier(dataSet);

        // Save the model
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("upload/random_forest_model.model"))) {
            oos.writeObject(rf);
        }
    }

}
