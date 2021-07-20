package com.kafka.repositiory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.model.Employee;
import com.kafka.model.Employee2;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo empRepo;

	@Autowired
	EmployeeRepo2 empRepo2;

	public List<Employee> getEmployee() {
		List<Employee> response = empRepo.findAll();
		return response;
	}

	public void saveEmployee(Employee2 emp) {
		empRepo2.save(emp);
	}

}
