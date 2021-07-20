package com.kafka.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Employee2")
public class Employee2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long employeeId;

	@Size(min = 3, max = 50)
	@Column(name = "empName", nullable = false)
	private String empName;

	@Size(min = 3, max = 50)
	@Column(name = "designation", nullable = false)
	private String designation;

	@Size(min = 3, max = 50)
	@Column(name = "jobLevel", nullable = false)
	private String jobLevel;

	@Size(min = 3, max = 10)
	@Column(name = "rating", nullable = false)
	private String rating;

	@NotNull
	@Column(name = "salary", nullable = true)
	private BigDecimal salary;

	public Employee2() {
	}

	public Employee2(long employeeId, String empName, String designation, String jobLevel, String rating,
			BigDecimal salary) {
		super();
		this.employeeId = employeeId;
		this.empName = empName;
		this.designation = designation;
		this.jobLevel = jobLevel;
		this.rating = rating;
		this.salary = salary;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

}
