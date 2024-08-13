package com.example.eployeeretentionpredection.controller;

import com.example.eployeeretentionpredection.entity.Employee;
import com.example.eployeeretentionpredection.pojo.DashboardPojo;
import com.example.eployeeretentionpredection.projection.CountProjection;
import com.example.eployeeretentionpredection.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {


    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PostMapping("/predict")
    public double predict(@RequestBody Employee employee) {
        return employeeService.predictRetentionPercentage(employee);
    }
    @GetMapping("/predict/{id}")
    public String predictById(@PathVariable int id) {
        return employeeService.predictById(id);
    }
    @GetMapping("/predict-svm/{id}")
    public String predictByIdSvm(@PathVariable int id) {
        return employeeService.predictByIdSvm(id);
    }

    @GetMapping("/dashboard")
    public CountProjection getDashData(){
        return employeeService.getEmployeeCount();
    }

}


