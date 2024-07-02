package com.example.eployeeretentionpredection.service;

import com.example.eployeeretentionpredection.entity.Employee;
import com.example.eployeeretentionpredection.pojo.DashboardPojo;
import com.example.eployeeretentionpredection.projection.CountProjection;
import com.example.eployeeretentionpredection.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    private RandomForest randomForest;

    public CountProjection getEmployeeCount(){
        return employeeRepository.getEmployeeCount();
    }

    public void loadTrainedModel() throws Exception {
        // Load the trained model
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("random_forest_model.model"));
        randomForest = (RandomForest) ois.readObject();
        ois.close();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        employee.setLikelyToLeave(predictRetention(employee));
        employee.setLikelyToLeavePer(predictRetentionPercentage(employee));
        return employeeRepository.save(employee);
    }

    public String predictById(int id) {
        Employee employee = employeeRepository.findById(id).get();
        return predictRetentionDescription(employee);
    }

    private boolean predictRetention(Employee employee) {
        try {
            // Load the trained model
            this.loadTrainedModel();

            // Define attributes
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("age"));
            attributes.add(new Attribute("gender", List.of("male", "female", "other")));
            attributes.add(new Attribute("address", List.of(employee.getAddress()))); // Add your actual address values
            attributes.add(new Attribute("jobTitle", List.of(employee.getJobTitle()))); // Add your actual job title values
            attributes.add(new Attribute("department", List.of("IT", "Operations", "HR", "Sales", "Marketing"))); // Add your actual department values
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

            // Create instance and set attribute values
            DenseInstance instance = new DenseInstance(dataSet.numAttributes());
            instance.setValue(attributes.get(0), employee.getAge());
            instance.setValue(attributes.get(1), employee.getGender());
            instance.setValue(attributes.get(2), employee.getAddress());
            instance.setValue(attributes.get(3), employee.getJobTitle());
            instance.setValue(attributes.get(4), employee.getDepartment());
            instance.setValue(attributes.get(5), employee.getLengthOfService());
            instance.setValue(attributes.get(6), employee.getPromotionsReceived());
            instance.setValue(attributes.get(7), employee.getTrainingOpportunities());
            instance.setValue(attributes.get(8), employee.getWorkingEnvironment());
            instance.setValue(attributes.get(9), employee.getManagementQuality());
            instance.setValue(attributes.get(10), employee.getJobSatisfaction());
            instance.setValue(attributes.get(11), employee.getPersonalDevelopmentOpportunities());

            // Add instance to data set
            dataSet.add(instance);

            // Make prediction
            double result = randomForest.classifyInstance(dataSet.instance(0));
            return result == 0; // Assuming 'yes' is the first class value (index 0)

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double predictRetentionPercentage(Employee employee) {
        try {
            // Load the trained model
            this.loadTrainedModel();

            // Define attributes
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("age"));
            attributes.add(new Attribute("gender", List.of("male", "female", "other")));
            attributes.add(new Attribute("address", List.of(employee.getAddress())));
            attributes.add(new Attribute("jobTitle", List.of(employee.getJobTitle())));
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

            // Create instance and set attribute values
            DenseInstance instance = new DenseInstance(dataSet.numAttributes());
            instance.setValue(attributes.get(0), employee.getAge());
            instance.setValue(attributes.get(1), employee.getGender());
            instance.setValue(attributes.get(2), employee.getAddress());
            instance.setValue(attributes.get(3), employee.getJobTitle());
            instance.setValue(attributes.get(4), employee.getDepartment());
            instance.setValue(attributes.get(5), employee.getLengthOfService());
            instance.setValue(attributes.get(6), employee.getPromotionsReceived());
            instance.setValue(attributes.get(7), employee.getTrainingOpportunities());
            instance.setValue(attributes.get(8), employee.getWorkingEnvironment());
            instance.setValue(attributes.get(9), employee.getManagementQuality());
            instance.setValue(attributes.get(10), employee.getJobSatisfaction());
            instance.setValue(attributes.get(11), employee.getPersonalDevelopmentOpportunities());

            // Add instance to data set
            dataSet.add(instance);

            // Get probability distribution
            double[] distribution = randomForest.distributionForInstance(dataSet.instance(0));
            double probabilityToLeave = distribution[0]; // Assuming 'yes' is the first class value (index 0)
            return probabilityToLeave * 100;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String predictRetentionDescription(Employee employee) {
        try {
            // Load the trained model
            this.loadTrainedModel();

            // Define attributes
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("age"));
            attributes.add(new Attribute("gender", List.of("male", "female", "other")));
            attributes.add(new Attribute("address", List.of(employee.getAddress())));
            attributes.add(new Attribute("jobTitle", List.of(employee.getJobTitle())));
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

            // Create instance and set attribute values
            DenseInstance instance = new DenseInstance(dataSet.numAttributes());
            instance.setValue(attributes.get(0), employee.getAge());
            instance.setValue(attributes.get(1), employee.getGender());
            instance.setValue(attributes.get(2), employee.getAddress());
            instance.setValue(attributes.get(3), employee.getJobTitle());
            instance.setValue(attributes.get(4), employee.getDepartment());
            instance.setValue(attributes.get(5), employee.getLengthOfService());
            instance.setValue(attributes.get(6), employee.getPromotionsReceived());
            instance.setValue(attributes.get(7), employee.getTrainingOpportunities());
            instance.setValue(attributes.get(8), employee.getWorkingEnvironment());
            instance.setValue(attributes.get(9), employee.getManagementQuality());
            instance.setValue(attributes.get(10), employee.getJobSatisfaction());
            instance.setValue(attributes.get(11), employee.getPersonalDevelopmentOpportunities());

            // Add instance to data set
            dataSet.add(instance);

//            // Get probability distribution
//            double[] distribution = randomForest.distributionForInstance(dataSet.instance(0));
//            double probabilityToLeave = distribution[0]; // Assuming 'yes' is the first class value (index 0)
//            return probabilityToLeave * 100; // Convert to percentage
            // Get probability distribution
            double[] distribution = randomForest.distributionForInstance(dataSet.instance(0));
            double probabilityToLeave = distribution[dataSet.classAttribute().indexOfValue("yes")]; // Correctly index 'yes'

            // Scale probability to percentage
            double percentageToLeave = probabilityToLeave * 100;

            // Determine likelihood group
            String likelihoodGroup;
            if (percentageToLeave < 25) {
                likelihoodGroup = "Low";
            } else if (percentageToLeave < 50) {
                likelihoodGroup = "Medium";
            } else if (percentageToLeave < 75) {
                likelihoodGroup = "High";
            } else {
                likelihoodGroup = "Very High";
            }

            // Return the response string
            return String.format("Employee is %s likely to leave with a probability of %.2f%%", likelihoodGroup, percentageToLeave);


        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}