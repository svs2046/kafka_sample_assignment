package com.kafka.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafka.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
