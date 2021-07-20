
package com.kafka.repositiory;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.kafka.model.Employee2;

public class EmployeeServiceTest {

	@InjectMocks
	EmployeeService empService;

	@Mock
	EmployeeRepo empRepo;

	@Mock
	EmployeeRepo2 empRepo2;

	@Spy
	List<Employee2> employees = new ArrayList<Employee2>();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveEmployee() {
		Employee2 savedEmp = empRepo2.save(new Employee2(123, "XYZ", "TA", "JL4", "1", new BigDecimal(1000)));
		empService.saveEmployee(savedEmp);
	}

	@Test
	public void findAllEmployees() {
		when(empRepo2.findAll()).thenReturn(employees);
		junit.framework.Assert.assertEquals(empService.getEmployee(), employees);
	}
}
