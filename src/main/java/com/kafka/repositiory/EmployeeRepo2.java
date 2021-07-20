package com.kafka.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafka.model.Employee2;

public interface EmployeeRepo2 extends JpaRepository<Employee2, String> {

}
