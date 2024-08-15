
package com.example.eployeeretentionpredection.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
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
        FileInputStream excelFile = new FileInputStream(System.getProperty("user.dir") + "/upload/data-source/ml_training_testing_data.xlsx");
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
        FileInputStream excelFile = new FileInputStream(System.getProperty("user.dir") + "/upload/data-source/ml_training_testing_data.xlsx");
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
        attributes.add(new Attribute("motivation"));
        attributes.add(new Attribute("careerDevelopment"));
        attributes.add(new Attribute("workLifeBalance"));
        attributes.add(new Attribute("performanceManagement"));
        attributes.add(new Attribute("performanceOpportunities"));
        attributes.add(new Attribute("jobSecurity"));
        attributes.add(new Attribute("flexibility"));
        attributes.add(new Attribute("stressAtWork"));
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
            instance.setValue(attributes.get(12), row.getCell(12).getNumericCellValue());
            instance.setValue(attributes.get(13), row.getCell(13).getNumericCellValue());
            instance.setValue(attributes.get(14), row.getCell(14).getNumericCellValue());
            instance.setValue(attributes.get(15), row.getCell(15).getNumericCellValue());
            instance.setValue(attributes.get(16), row.getCell(16).getNumericCellValue());
            instance.setValue(attributes.get(17), row.getCell(17).getNumericCellValue());
            instance.setValue(attributes.get(18), row.getCell(18).getNumericCellValue());
            instance.setValue(attributes.get(19), row.getCell(19).getNumericCellValue());
            instance.setValue(attributes.get(20), row.getCell(20).getStringCellValue());
            instance.setValue(attributes.get(21), row.getCell(21).getStringCellValue());

            dataSet.add(instance);
        }

        workbook.close();

        // Train the Random Forest model
        RandomForest rf = new RandomForest();
        rf.buildClassifier(dataSet);


        // Split the dataset into 70% training and 30% testing
        Instances[] splitData = splitDataset(dataSet, 70.0);
        Instances trainingSet = splitData[0];
        Instances testSet = splitData[1];

        // Train the Random Forest model on the training set
        RandomForest rf2 = new RandomForest();
        rf2.buildClassifier(trainingSet);

        // Evaluate the model on the testing set
        Evaluation evaluation = new Evaluation(trainingSet);
        evaluation.evaluateModel(rf2, testSet);

        // Print the accuracy
        System.out.println("Result fo Random Forest");
        System.out.println("Accuracy: " + evaluation.pctCorrect() + "%");
        System.out.println("Confusion Matrix: " + evaluation.toMatrixString());
        System.out.println("ROC CURVE: " +  evaluation.areaUnderROC(testSet.classAttribute().indexOfValue("yes")));

        Evaluation evaluationSVMForF1Score = new Evaluation(testSet);
        evaluationSVMForF1Score.evaluateModel(rf2, testSet);
        System.out.println("F1 Score for Yes : Random Forest : " + calculateF1Score(evaluationSVMForF1Score, "yes"));
        System.out.println("F1 Score for No : Random Forest : " + calculateF1Score(evaluationSVMForF1Score, "no"));

        Evaluation evaluationSVMForSpecificity = new Evaluation(testSet);
        evaluationSVMForSpecificity.evaluateModel(rf2, testSet);
        System.out.println("specificity for Yes: " + calculateSpecificity(evaluationSVMForSpecificity, "yes"));
        System.out.println("specificity for No: " + calculateSpecificity(evaluationSVMForSpecificity, "no"));

        // Save the model
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("upload/random_forest_model.model"))) {
            oos.writeObject(rf);
        }
    }


    public void trainLogisticModel(MultipartFile file) throws Exception {
        // Save the uploaded Excel file to a directory
        Files.write(Path.of(System.getProperty("user.dir") + "/upload/data-source/" + file.getOriginalFilename()), file.getBytes());

        // Load the Excel file
        FileInputStream excelFile = new FileInputStream(System.getProperty("user.dir") + "/upload/data-source/ml_training_testing_data.xlsx");
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
        attributes.add(new Attribute("motivation"));
        attributes.add(new Attribute("careerDevelopment"));
        attributes.add(new Attribute("workLifeBalance"));
        attributes.add(new Attribute("performanceManagement"));
        attributes.add(new Attribute("performanceOpportunities"));
        attributes.add(new Attribute("jobSecurity"));
        attributes.add(new Attribute("flexibility"));
        attributes.add(new Attribute("stressAtWork"));
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
            instance.setValue(attributes.get(12), row.getCell(12).getNumericCellValue());
            instance.setValue(attributes.get(13), row.getCell(13).getNumericCellValue());
            instance.setValue(attributes.get(14), row.getCell(14).getNumericCellValue());
            instance.setValue(attributes.get(15), row.getCell(15).getNumericCellValue());
            instance.setValue(attributes.get(16), row.getCell(16).getNumericCellValue());
            instance.setValue(attributes.get(17), row.getCell(17).getNumericCellValue());
            instance.setValue(attributes.get(18), row.getCell(18).getNumericCellValue());
            instance.setValue(attributes.get(19), row.getCell(19).getNumericCellValue());
            instance.setValue(attributes.get(20), row.getCell(20).getStringCellValue());
            instance.setValue(attributes.get(21), row.getCell(21).getStringCellValue());

            dataSet.add(instance);
        }

        workbook.close();

        // Train the Logistic Regression model
        Logistic logistic = new Logistic();
        logistic.buildClassifier(dataSet);

        // Split the dataset into 70% training and 30% testing
        Instances[] splitData = splitDataset(dataSet, 70.0);
        Instances trainingSet = splitData[0];
        Instances testSet = splitData[1];

        // Train the Random Forest model on the training set
        Logistic lr2 = new Logistic();
        lr2.buildClassifier(trainingSet);

        // Evaluate the model on the testing set
        Evaluation evaluation = new Evaluation(trainingSet);
        evaluation.evaluateModel(lr2, testSet);


        // Print the accuracy
        System.out.println("Result for Logistic  Regression");
        System.out.println("Accuracy: " + evaluation.pctCorrect() + "%");
        System.out.println("Confusion Matrix: " + evaluation.toMatrixString());
        System.out.println("ROC CURVE: " +  evaluation.areaUnderROC(testSet.classAttribute().indexOfValue("yes")));

        Evaluation evaluationSVMForF1Score = new Evaluation(testSet);
        evaluationSVMForF1Score.evaluateModel(lr2, testSet);
        System.out.println("F1 Score for Yes : Logistic  Regression : " + calculateF1Score(evaluationSVMForF1Score, "yes"));
        System.out.println("F1 Score for No :Logistic  Regression : " + calculateF1Score(evaluationSVMForF1Score, "no"));

        Evaluation evaluationSVMForSpecificity = new Evaluation(testSet);
        evaluationSVMForSpecificity.evaluateModel(lr2, testSet);
        System.out.println("specificity for Yes: " + calculateSpecificity(evaluationSVMForSpecificity, "yes"));
        System.out.println("specificity for No: " + calculateSpecificity(evaluationSVMForSpecificity, "no"));


        // Save the model
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("upload/logistic_regression_model.model"))) {
            oos.writeObject(logistic);
        }
    }

    public void trainSVMModel(MultipartFile file) throws Exception {
        // Save the uploaded Excel file to a directory
        Files.write(Path.of(System.getProperty("user.dir") + "/upload/data-source/" + file.getOriginalFilename()), file.getBytes());

        // Load the Excel file
        FileInputStream excelFile = new FileInputStream(System.getProperty("user.dir") + "/upload/data-source/ml_training_testing_data.xlsx");
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
        attributes.add(new Attribute("motivation"));
        attributes.add(new Attribute("careerDevelopment"));
        attributes.add(new Attribute("workLifeBalance"));
        attributes.add(new Attribute("performanceManagement"));
        attributes.add(new Attribute("performanceOpportunities"));
        attributes.add(new Attribute("jobSecurity"));
        attributes.add(new Attribute("flexibility"));
        attributes.add(new Attribute("stressAtWork"));
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
            instance.setValue(attributes.get(12), row.getCell(12).getNumericCellValue());
            instance.setValue(attributes.get(13), row.getCell(13).getNumericCellValue());
            instance.setValue(attributes.get(14), row.getCell(14).getNumericCellValue());
            instance.setValue(attributes.get(15), row.getCell(15).getNumericCellValue());
            instance.setValue(attributes.get(16), row.getCell(16).getNumericCellValue());
            instance.setValue(attributes.get(17), row.getCell(17).getNumericCellValue());
            instance.setValue(attributes.get(18), row.getCell(18).getNumericCellValue());
            instance.setValue(attributes.get(19), row.getCell(19).getNumericCellValue());
            instance.setValue(attributes.get(20), row.getCell(20).getStringCellValue());
            instance.setValue(attributes.get(21), row.getCell(21).getStringCellValue());

            dataSet.add(instance);
        }

        workbook.close();

        // Train the SVM model
        SMO svm = new SMO();
        svm.buildClassifier(dataSet);

        // Split the dataset into 70% training and 30% testing
        Instances[] splitData = splitDataset(dataSet, 70.0);
        Instances trainingSet = splitData[0];
        Instances testSet = splitData[1];

        // Train the Random Forest model on the training set
        SMO svm2 = new SMO();
        svm2.buildClassifier(trainingSet);

        // Evaluate the model on the testing set
        Evaluation evaluation = new Evaluation(trainingSet);
        evaluation.evaluateModel(svm2, testSet);

        // Print the accuracy
        System.out.println("Result fo SVM");
        System.out.println("Accuracy: " + evaluation.pctCorrect() + "%");
        System.out.println("Confusion Matrix: " + evaluation.toMatrixString());
        System.out.println("ROC CURVE: " +  evaluation.areaUnderROC(testSet.classAttribute().indexOfValue("yes")));

        Evaluation evaluationSVMForF1Score = new Evaluation(testSet);
        evaluationSVMForF1Score.evaluateModel(svm2, testSet);
        System.out.println("F1 Score for Yes : SVM : " + calculateF1Score(evaluationSVMForF1Score, "yes"));
        System.out.println("F1 Score for No : SVM : " + calculateF1Score(evaluationSVMForF1Score, "no"));

        Evaluation evaluationSVMForSpecificity = new Evaluation(testSet);
        evaluationSVMForSpecificity.evaluateModel(svm2, testSet);
        System.out.println("specificity for Yes: " + calculateSpecificity(evaluationSVMForSpecificity, "yes"));
        System.out.println("specificity for No: " + calculateSpecificity(evaluationSVMForSpecificity, "no"));



        // Save the model
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("upload/svm_model.model"))) {
            oos.writeObject(svm);
        }
    }

    public double calculateSpecificity(Evaluation evaluation, String positiveClassLabel) throws Exception {
        int classIndex = evaluation.getHeader().classAttribute().indexOfValue(positiveClassLabel);

        double trueNegatives = evaluation.numTrueNegatives(classIndex);
        double falsePositives = evaluation.numFalsePositives(classIndex);

        return trueNegatives / (trueNegatives + falsePositives);
    }

    public double calculateF1Score(Evaluation evaluation, String positiveClassLabel) throws Exception {
        int classIndex = evaluation.getHeader().classAttribute().indexOfValue(positiveClassLabel);

        double precision = evaluation.precision(classIndex);
        double recall = evaluation.recall(classIndex);

        System.out.println("precision:" + precision);
        System.out.println("recall:" + recall);

        return 2 * ((precision * recall) / (precision + recall));
    }


    public Instances[] splitDataset(Instances dataset, double trainSizePercent) throws Exception {
        // Calculate the number of instances for the training set
        int trainSize = (int) Math.round(dataset.numInstances() * trainSizePercent / 100);
        int testSize = dataset.numInstances() - trainSize;

        // Split the dataset into training and testing sets
        Instances train = new Instances(dataset, 0, trainSize);
        Instances test = new Instances(dataset, trainSize, testSize);

        return new Instances[] { train, test };
    }
}
