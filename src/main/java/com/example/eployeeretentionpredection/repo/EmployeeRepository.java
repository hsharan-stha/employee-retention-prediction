package com.example.eployeeretentionpredection.repo;

import com.example.eployeeretentionpredection.entity.Employee;
import com.example.eployeeretentionpredection.projection.CountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    @Query(value = "select (select count(*) from employee) as \"empCount\",\n" +
            "       (SELECT\n" +
            "       (SUM(CASE WHEN likely_to_leave = 'false' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS \"retentionCount\"\n" +
            "       FROM employee)\n" +
            "       ,(SELECT\n" +
            "            AVG(job_satisfaction)\n" +
            "            FROM employee) as \"jobSatisCount\"",nativeQuery = true)
    CountProjection getEmployeeCount();


}
