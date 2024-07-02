package com.example.eployeeretentionpredection.repo;

import com.example.eployeeretentionpredection.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
