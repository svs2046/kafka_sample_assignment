package com.kafka.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.Gson;

@Entity
@Table(name = "Employee1")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Column(name = "salary", nullable = false)
	private BigDecimal salary;

	public Employee() {
	}

	public Employee(long employeeId, String empName, String designation, String jobLevel, String rating,
			BigDecimal salary) {
		super();
		this.employeeId = employeeId;
		this.empName = empName;
		this.designation = designation;
		this.jobLevel = jobLevel;
		this.rating = rating;
		this.salary = salary;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
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

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
