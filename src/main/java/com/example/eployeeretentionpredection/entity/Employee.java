package com.example.eployeeretentionpredection.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int age;
    private String fullName;
    private String gender;
    private String address;
    private String jobTitle;
    private String department;
    private int lengthOfService;
    private int promotionsReceived;
    private String trainingOpportunities;
    private int workingEnvironment;
    private int managementQuality;
    private int jobSatisfaction;
    private int personalDevelopmentOpportunities;
    private String leftReason;
    private boolean likelyToLeave;
    private double likelyToLeavePer;
}