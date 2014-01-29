package br.com.cowabunga.json;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.cowabunga.domain.Employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("serial")
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class EmployeeJson implements Serializable {

	private Long id;
	private String firstName;
	private String lastName;
	private BigDecimal salary;
	private String role;

	public EmployeeJson() {	}
	
	public EmployeeJson(Employee employee) {
		this.id = employee.getId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.role = employee.getRole();
		this.salary = employee.getSalary();
	}
	
	public Employee toDomain() {
		Employee employee = new Employee();
		
		employee.setId( this.id );
		employee.setFirstName( this.firstName );
		employee.setLastName( this.lastName );
		employee.setSalary( this.salary );
		employee.setRole( this.role );
		
		return employee;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}